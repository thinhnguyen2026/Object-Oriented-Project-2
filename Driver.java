import java.util.Scanner;

public class Driver {
    private static Location currLocation;
    private static ContainerItem myInventory = new ContainerItem("Backpack", "Container", "A sturdy backpack.");
    
    public static void main(String[] args) {
        createWorld();
       
        Scanner myObj = new Scanner(System.in);

        System.out.println("Welcome to my Haunted Thanksgiving Game!");
        while (true){
            System.out.print("Enter a command: ");
            String command = myObj.nextLine();
            String[] commandParts = command.split(" ");

            switch (commandParts[0].toLowerCase()) {
                case "quit":
                    System.out.println("Exiting the game...");
                    myObj.close();
                    return;
                    
                case "look":
                    System.out.println(currLocation.getName() + " - " + currLocation.getDescription() + " currently has the following items:");
                    for (int i = 0; i < currLocation.numItems(); i++) {
                        System.out.println("+ " + currLocation.getItem(i).getName());
                    }
                    break;

                case "examine":
                    if (commandParts.length > 1) {
                        Item item = currLocation.getItem(commandParts[1]);
                        if (item instanceof ContainerItem) {
                            System.out.println(item.toString());
                        }
                        else if (item != null) {
                            System.out.println(item.toString());
                        }
                        else {
                            System.out.println("Cannot find that item");
                        }
                    } else {
                        System.out.println("Please specify an item to examine.");
                    }
                    break;

                case "go": 
                    if(commandParts.length == 1) {
                        System.out.println("You did not tell me a direction that you want to move in");
                    } 
                    else {
                        String direction = commandParts[1].toLowerCase();
                        if(direction.equals("north")||direction.equals("south")||direction.equals("west")||direction.equals("east")){
                            if(currLocation.canMove(direction)) {
                                currLocation = currLocation.getLocation(direction);
                            } else {
                                System.out.println("I am unable to move in that direction");
                            }
                        } 
                        else {
                            System.out.println("Please enter a valid direction");
                        }
                    }
                    break;
                
                case "inventory":
                    System.out.println(myInventory);
				    break;

                case "take":
                    if (commandParts.length == 2) {
                        String itemName = commandParts[1];
                        if (currLocation.hasItem(itemName)) {
                            Item item = currLocation.getItem(itemName);
                            myInventory.addItem(item);
                            currLocation.removeItem(commandParts[1]);
                            System.out.println("You took the " + itemName);
                        } else {
                            System.out.println("This location does not have this item");
                        }
                    } else if (commandParts.length == 4 && commandParts[2].equalsIgnoreCase("from")) {
                        String itemName = commandParts[1];
                        String containerName = commandParts[3];
                        Item containerItem = currLocation.getItem(containerName);
                        if (containerItem instanceof ContainerItem) {
                            ContainerItem container = (ContainerItem) containerItem;
                            if (container.hasItem(itemName)) {
                                Item item = container.removeItem(itemName);
                                myInventory.addItem(item);
                                System.out.println("You took the " + itemName + " from " + containerName);
                            } else {
                                System.out.println("That item is not in the " + containerName);
                            }
                        } else {
                            System.out.println("No such container found.");
                        }
                    } else {
                        System.out.println("Invalid command format.");
                    }
                    break;

                case "drop":
                if (commandParts.length > 1) {
                    if (myInventory.hasItem(commandParts[1])) {
                        Item item = myInventory.removeItem(commandParts[1]);
                        currLocation.addItem(item);  
                        System.out.println("You dropped the " + commandParts[1]);
                    } else {
                        System.out.println("You do not have this item in your inventory");
                    }
                } else {
                    System.out.println("Please enter a valid item");
                }
                break;

                case "put":
                if (commandParts.length == 4 && commandParts[2].equalsIgnoreCase("in")) {
                    String itemName = commandParts[1];
                    String containerName = commandParts[3];
                    if (myInventory.hasItem(itemName)) {
                        Item item = myInventory.removeItem(itemName);
                        Item containerItem = currLocation.getItem(containerName);
                        if (containerItem instanceof ContainerItem) {
                            ContainerItem container = (ContainerItem) containerItem;
                            container.addItem(item);
                            System.out.println("You put the " + itemName + " in " + containerName);
                        } 
                        else {
                            System.out.println("No such container found.");
                        }
                    } 
                    else {
                        System.out.println("You don't have that item in your inventory.");
                    }
                } 
                else {
                    System.out.println("Invalid command format.");
                }
                break;
                
                case "help":
                    printHelp();
                    break;

                default:
                    System.out.println("I don't know how to do that, type 'help' for a list of commands");
                    break;
            }
        }
    }

    public static void createWorld(){
        Location kitchen = new Location("Kitchen","A dark and damp kitchen with a flickering light");
        Location hallway = new Location("Hallway","There are smears of gravy on the walls");
        Location bedroom = new Location("Bedroom","A messy bedroom with a lot of dust and cobwebs");
        Location laundry = new Location("Laundry","A faint gobbling is heard over the sound of a humming dryer that was left on");

        kitchen.connect("north", hallway);
        hallway.connect("south", kitchen);
        kitchen.connect("west", bedroom);
        bedroom.connect("east", kitchen);
        kitchen.connect("south", laundry);
        laundry.connect("north", kitchen);

        kitchen.addItem(new Item("Knife", "Weapon", "It's sharp and pointy"));
        kitchen.addItem(new Item("Turkey", "Food", "It's cold and now but delicious"));
        bedroom.addItem(new Item("Lamp","Item","An old lamp with a bit dust" ));
        laundry.addItem(new Item("Detergent","Item","A dirty detergent bottle with blood on the surface"));
        hallway.addItem(new Item("Plate","Item","A dirty plate with one corner broken"));

        currLocation = hallway;

        ContainerItem chest = new ContainerItem("Chest", "Container", "An old wooden chest covered by a towel");
        chest.addItem(new Item("Gemstone", "Item", "A shiny blue gemstone"));
        chest.addItem(new Item("Dictionary", "Item", "An old dictionary with lots of dust"));
        chest.addItem(new Item("Key", "Item", "A key made of polished silver with intricate designs"));
        hallway.addItem(chest);
        
        ContainerItem desk = new ContainerItem("Desk", "Container", "Old table with many ink stains");
        desk.addItem(new Item("Pen", "Item", "An ancient brush"));
        desk.addItem(new Item("Map", "Item", "A faded map showing unknown territories"));
        desk.addItem(new Item("Diary", "Item", "A small diary with a lock, filled with secrets"));
        laundry.addItem(desk);

        ContainerItem wardrobe = new ContainerItem("Wardrobe", "Container", "An antique wooden wardrobe with intricate carvings");
        wardrobe.addItem(new Item("Candle", "Item", "An old, half-melted candle"));
        wardrobe.addItem(new Item("Mirror", "Item", "A small, ornate hand mirror"));
        wardrobe.addItem(new Item("Robe", "Item", "A velvet robe with golden trim"));
        bedroom.addItem(wardrobe);
    }

    public static void printHelp() {
        System.out.println("'GO' command moves to the direction including north, south, west and east");
        System.out.println("'LOOK' command prints items that are found at that location currently" );
        System.out.println("'EXAMINE' command prints a description of surroundings items");
        System.out.println("'TAKE' command takes an item from the current location and put it into inventory");
        System.out.println("'DROP' command drops an item from inventory and put it at the current location");
        System.out.println("'INVENTORY' command lists the objects that you already took");
        System.out.println("'QUIT' command exits the game");
        System.out.println("'HELP' command gives you specific command descriptions to help you run the game");
    }
}

