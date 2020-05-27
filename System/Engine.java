package System;

/**
 * Engine class holds the main function to start the program by creating a new application object then calling the launch function
 */
public class Engine {
    public static void main(String[] args) {
        Application app = new Application();
        app.launch();
    }
}
