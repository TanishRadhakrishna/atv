import java.util.Timer;
import java.util.TimerTask;

public class TrafficLightController {
    // Define states
    private enum State {
        GREEN, YELLOW, RED, WALK, DONT_WALK
    }

    // Time durations for each state (in seconds)
    private static final int GREEN_TIME = 30;
    private static final int YELLOW_TIME = 5;
    private static final int RED_TIME = 30;
    private static final int WALK_TIME = 20;

    private State currentState;
    private final Timer timer;
    private int timerCount;

    // Flag for pedestrian request
    private boolean pedestrianRequested;

    public TrafficLightController() {
        currentState = State.RED; // Initial state
        timerCount = 0;
        timer = new Timer();
        pedestrianRequested = false; // Initialize pedestrian request flag
        startTrafficLightCycle();
    }

    // Start the traffic light cycle
    private void startTrafficLightCycle() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateState();
            }
        }, 0, 1000); // Update every second
    }

    // Update the traffic light state based on the current state and timer
    private void updateState() {
        switch (currentState) {
            case GREEN -> {
                if (timerCount >= GREEN_TIME) {
                    currentState = State.YELLOW;
                    timerCount = 0;
                    System.out.println("Light changed to YELLOW");
                }
            }
            case YELLOW -> {
                if (timerCount >= YELLOW_TIME) {
                    currentState = State.RED;
                    timerCount = 0;
                    System.out.println("Light changed to RED");
                }
            }
            case RED -> {
                if (timerCount >= RED_TIME) {
                    if (!pedestrianRequested) { // Only change to GREEN if no pedestrian request
                        currentState = State.GREEN;
                        timerCount = 0;
                        System.out.println("Light changed to GREEN");
                    }
                }
            }
            case WALK -> {
                if (timerCount >= WALK_TIME) {
                    currentState = State.DONT_WALK; // Change to don't walk after walk time
                    timerCount = 0;
                    System.out.println("Signal changed to DON'T WALK");
                }
            }
            case DONT_WALK -> {
                if (timerCount >= RED_TIME) { // After some time, change back to RED
                    currentState = State.RED;
                    timerCount = 0;
                    System.out.println("Light changed to RED");
                }
            }
        }

        // Check for pedestrian requests during RED state
        if (currentState == State.RED && pedestrianRequested) {
            currentState = State.WALK; // Change to walk signal when requested
            timerCount = 0;
            System.out.println("Pedestrian request received! Signal changed to WALK");
            pedestrianRequested = false; // Reset request after processing
        }

        timerCount++;
    }

    // Method to simulate vehicle detection
    public void vehicleDetected() {
        if (currentState == State.RED) {
            currentState = State.GREEN; // Change to green when a vehicle is detected
            timerCount = 0;
            System.out.println("Vehicle detected! Light changed to GREEN");
        }
    }

    // Method to simulate emergency vehicle detection
    public void emergencyVehicleDetected() {
        currentState = State.GREEN; // Immediately change to green for emergency vehicles
        timerCount = 0;
        System.out.println("Emergency vehicle detected! Light changed to GREEN");
    }

    // Method to simulate pedestrian request
    public void pedestrianRequest() {
        pedestrianRequested = true; // Set flag for pedestrian request
        System.out.println("Pedestrian request received! Waiting for signal change.");
    }

    public static void main(String[] args) {
        TrafficLightController controller = new TrafficLightController();

        // Simulating events for testing purposes in a sequence with delays
        try {
            Thread.sleep(10000); // Wait for 10 seconds before simulating vehicle detection
            controller.vehicleDetected();

            Thread.sleep(20000); // Wait for another 20 seconds before simulating pedestrian request
            controller.pedestrianRequest();

            Thread.sleep(15000); // Wait for another 15 seconds before simulating emergency vehicle detection
            controller.emergencyVehicleDetected();

            Thread.sleep(50000); // Allow some time for the simulation to run and observe behavior

        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted: " + e.getMessage());
        } finally {
            controller.timer.cancel(); // Stop the timer when done testing.
        }
        
        System.out.println("Simulation ended.");
    }
}
