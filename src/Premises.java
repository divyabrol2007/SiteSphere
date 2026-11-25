import java.util.ArrayList;

// ==========================================================
// SITESPHERE - PREMISES MANAGEMENT
// Demonstrates:
// 1. Encapsulation
// 2. Composition
// 3. ArrayList
// 4. Constructors and methods
// ==========================================================

public class Premises {

    private int premisesId;
    private String premisesName;
    private String address;
    private String category;

    // Composition: Premises contains multiple Room objects
    private ArrayList<Room> rooms;

    public Premises(int premisesId, String premisesName,
                    String address, String category) {

        this.premisesId = premisesId;
        this.premisesName = premisesName;
        this.address = address;
        this.category = category;

        rooms = new ArrayList<>();
    }

    // Getters
    public int getPremisesId() {
        return premisesId;
    }

    public String getPremisesName() {
        return premisesName;
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    // Setters
    public void setPremisesName(String premisesName) {
        this.premisesName = premisesName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Add room
    public void addRoom(Room room) {

        if (room != null) {
            rooms.add(room);
            System.out.println("Room added successfully!");
        }
    }

    // Remove room
    public void removeRoom(int roomId) {

        boolean removed = rooms.removeIf(
            room -> room.getRoomId() == roomId
        );

        if (removed) {
            System.out.println("Room removed successfully!");
        } else {
            System.out.println("Room not found!");
        }
    }

    // Display all rooms
    public void displayRooms() {

        System.out.println("\n====== ROOM DETAILS ======");

        if (rooms.isEmpty()) {
            System.out.println("No rooms registered.");
            return;
        }

        for (Room room : rooms) {
            room.displayRoomDetails();
        }
    }

    // Display premises
    public void displayPremisesDetails() {

        System.out.println("\n====== PREMISES DETAILS ======");
        System.out.println("Premises ID: " + premisesId);
        System.out.println("Name: " + premisesName);
        System.out.println("Address: " + address);
        System.out.println("Category: " + category);
        System.out.println("Total Rooms: " + rooms.size());
    }
}


// ==========================================================
// ROOM CLASS
// ==========================================================

class Room {

    private int roomId;
    private String roomNumber;
    private String roomType;
    private int capacity;

    public Room(int roomId, String roomNumber,
                String roomType, int capacity) {

        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.capacity = capacity;
    }

    // Getters
    public int getRoomId() {
        return roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    // Setters
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // Display room details
    public void displayRoomDetails() {

        System.out.println("-------------------------");
        System.out.println("Room ID: " + roomId);
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Room Type: " + roomType);
        System.out.println("Capacity: " + capacity);
    }
}