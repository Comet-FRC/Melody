package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import edu.wpi.first.units.measure.Distance;

public class LED extends SubsystemBase {
    private final AddressableLED led;
    private final AddressableLEDBuffer ledBuffer;
    private final NetworkTable gameTable;
    
    // Rainbow pattern properties
    private LEDPattern rainbow = LEDPattern.rainbow(255, 128);
    private static Distance ledSpacing = Meters.of(1.0 / 120.0);
    private LEDPattern scrollingRainbow = rainbow.scrollAtAbsoluteSpeed(MetersPerSecond.of(1), ledSpacing);
    
    // State tracking
    private boolean isRainbow = true;
    private boolean isInGame = false;
    private double[] lastColor = new double[]{0, 0, 0};
    private String currentDifficulty = "medium";  // Default difficulty

    public LED() {
        // Initialize LED hardware
        led = new AddressableLED(2);
        ledBuffer = new AddressableLEDBuffer(190);
        led.setLength(ledBuffer.getLength());
        led.start();

        // Initialize NetworkTables connection
        gameTable = NetworkTableInstance.getDefault().getTable("LEDGame");
        
        // Set default state to rainbow
        turnOn();
    }

    @Override
    public void periodic() {
        // Check if game mode should be active
        boolean gameActive = gameTable.getEntry("game_active").getBoolean(false);
        String gameState = gameTable.getEntry("game_state").getString("");
        
        if (gameActive && !isInGame) {
            startGame();  // Enter game mode
        } else if (!gameActive && isInGame) {
            stopGame();   // Exit game mode
        }

        if (isInGame) {
            // Check for color updates from the game
            double[] newColor = gameTable.getEntry("current_color").getDoubleArray(lastColor);
            
            // Only update if the color has changed
            if (newColor[0] != lastColor[0] || newColor[1] != lastColor[1] || newColor[2] != lastColor[2]) {
                setAllLEDs((int)newColor[0], (int)newColor[1], (int)newColor[2]);
                lastColor = newColor.clone();  // Use clone to avoid reference issues
            }
        }
        else if (isRainbow) {
            // Update rainbow pattern
            scrollingRainbow.applyTo(ledBuffer);
            led.setData(ledBuffer);
        }
        else {
            // If neither in game nor rainbow mode, turn on rainbow
            turnOn();
        }

        // Check for game end states
        if (gameState.equals("wrong")) {
            stopGame();
        }
    }

    public void setAllLEDs(int red, int green, int blue) {
        for (int i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setRGB(i, red, blue, green); // Swapped green and blue
        }
        led.setData(ledBuffer);
    }
    
    public void setColor(int index, int red, int green, int blue) {
        if (index >= 0 && index < ledBuffer.getLength()) {
            ledBuffer.setRGB(index, red, blue, green); // Swapped green and blue
            led.setData(ledBuffer);
        }
    }
    
    /**
     * Enables game mode 
     */
    public void startGame() {
        isInGame = true;
        isRainbow = false;
        System.out.println("LED Game Mode ON");
    }
    
    /**
     * Clears all LEDs (turns them off)
     */
    public void clear() {
        isRainbow = false;
        isInGame = false;
        setAllLEDs(0, 0, 0);
        System.out.println("LED is OFF");
    }

    /**
     * Enables rainbow pattern
     */
    public void turnOn() {
        isRainbow = true;
        isInGame = false;
        scrollingRainbow.applyTo(ledBuffer);
        led.setData(ledBuffer);
        System.out.println("LED Rainbow ON");
    }

    /**
     * Turns off all LEDs
     */
    public void turnOff() {
        clear();
    }

    /**
     * Disables game mode and re-enables rainbow pattern
     */
    public void stopGame() {
        isInGame = false;
        turnOn();  // Re-enable rainbow pattern
        System.out.println("LED Game Mode OFF, Rainbow Mode ON");
    }

    /**
     * Gets the current state of game mode
     */
    public boolean isGameMode() {
        return isInGame;
    }

    /**
     * Gets the current state of rainbow mode
     */
    public boolean isRainbowMode() {
        return isRainbow;
    }

    /**
     * Gets the length of the LED strip
     */
    public int getLEDCount() {
        return ledBuffer.getLength();
    }
}