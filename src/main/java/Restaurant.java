import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();

    @Override
    public boolean equals(Object obj) {
        Restaurant theRestaurant = (Restaurant) obj;
        return this.getName().equalsIgnoreCase(theRestaurant.getName());
    }

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {

        LocalTime localTime = this.getCurrentTime();

        if(localTime.isAfter(this.openingTime) && localTime.isBefore(this.closingTime)) {
            return true;
        }

        return false;
    }

    public LocalTime getCurrentTime(){ return  LocalTime.now(); }

    public List<Item> getMenu() {
        List<Item> menuItems = new ArrayList<>(this.menu);
        return menuItems;
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }

    public String getName() {
        return name;
    }

    public int getTotalItemValue(ArrayList<String> itemNames) throws itemNotFoundException{
        int total = 0;
        for(String itemName : itemNames) {
            Item item = findItemByName(itemName);
            if(item != null) {
                total += item.getPrice();
            } else {
                throw new itemNotFoundException("Item Not Found: "+itemName);
            }
        }

        return total;
    }
}
