import java.util.ArrayList;
import java.util.HashMap;

public class Location {
    private String name;
    private String description;
    private ArrayList<Item> items;
    private HashMap<String, Location> connections;

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
        items = new ArrayList<Item>();
        connections = new HashMap<String, Location>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
  
    public void addItem(Item newItem) {
        items.add(newItem);
    }  

    public boolean hasItem(String itemName) {
		for(Item a : items) {
			if (itemName.equalsIgnoreCase(a.getName())){
				return true;
			}
		}
		return false;
	} 
  
    public Item getItem(String itemName) {
        if (hasItem(itemName)) {
            for(Item a : items) {
				if (itemName.equalsIgnoreCase(a.getName())){
					return a;
				}
			}
        }
        else {
            return null;
        }
        return null;
    }

	public Item getItem(int index) {
		if (index >= 0 && index < items.size()) {
            return items.get(index);
		}
		return null;
	}

    public int numItems() {
        return items.size();
    }

	public Item removeItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                items.remove(item);
                return item;
            }
        }
        return null;
    }

    public void connect(String direction, Location location){
        connections.put(direction, location);
    }

    public boolean canMove(String direciton){
        return connections.containsKey(direciton);
    }

    public Location getLocation(String direction){
        return connections.get(direction);
    }
}