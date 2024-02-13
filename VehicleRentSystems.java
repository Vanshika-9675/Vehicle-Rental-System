import java.util.*;

//abstract vehicle class
abstract class Vehicle{
    private String make;
    private String model;
    private int year;
    private int rental_price;
    private int vehicleNum;

    public Vehicle(String make, String model, int year, int rental_price, int vehicleNum) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.rental_price = rental_price;
        this.vehicleNum= vehicleNum;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getRental_price() {
        return rental_price;
    }
    public int getVehicleNum() {
        return vehicleNum;
    }

    abstract int RentalCost(int days);
    abstract void showDetails();
}

//car class
class Car extends Vehicle{
    private int numSeats;
    private String fuelType;

    public Car(String make, String model, int year, int rental_price, int numSeats, String fuelType,int vehicleNum) {
        super(make, model, year, rental_price,vehicleNum);
        this.numSeats = numSeats;
        this.fuelType = fuelType;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public String getFuelType() {
        return fuelType;
    }

    //implemented abstract methods 
    public int RentalCost(int d){
        return d*getRental_price();
    }

    public void showDetails(){
       System.out.println("CAR :"+ getMake()+ ", Number: "+ getVehicleNum());
    }
}

//other vehicle class
class OtherVehicles extends Vehicle{
    private int numSeats;
    private String fuelType;

    public OtherVehicles(String make, String model, int year, int rental_price, int numSeats, String fuelType,int vehicleNum) {
        super(make, model, year, rental_price,vehicleNum);
        this.numSeats = numSeats;
        this.fuelType = fuelType;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public String getFuelType() {
        return fuelType;
    }

    //implemented abstract methods 
    public int RentalCost(int d){
        return d*getRental_price();
    }

    public void showDetails(){
        System.out.println("Other vehicle :"+ getMake()+ ", Number: "+ getVehicleNum() + ", Rent: "+ getRental_price());
     }

}

//customer class for keeping track on registered user and rented vehicles
class Customer{
    private String name;
    private String email;

    public static ArrayList<Vehicle> rentedVehicles = new ArrayList<>();

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

   public static HashSet<String> set = new HashSet<>();
   public void registerCustomer(Customer c){
       set.add(c.getEmail());
   }
}

class RentalAgency{
   HashMap<Integer,Vehicle> agency = new HashMap<>();
   HashMap<Integer,Vehicle> mp = new HashMap<>();

   //function to add vehicles in the agency
   public void AddVehicles(Vehicle v){
       if(!agency.containsKey(v.getVehicleNum())){
           agency.put(v.getVehicleNum(), v);
           System.out.println("Vehicle added successfully!!");
       }
       else{
        System.out.println("Vehicle with this this number already exist!");
       }
   }

   //function to show available vehicles in the agency
   public void showAvailableVehicles(){
         if(!agency.isEmpty()){
             for (Vehicle v: agency.values()) {
                v.showDetails();
             }
         }
         else{
            System.out.println("Add some vehicles to the agency first!");
         }
   }
   
   //function to rent a vehicle
   public void rentingVehicle(int id, String email){
          if (Customer.set.contains(email) && agency.containsKey(id)) {
            mp.put(id,agency.get(id));
            Customer.rentedVehicles.add(agency.get(id));
            agency.remove(id);
            System.out.println("Vehicle rented successfully!");
          }
          else{
            System.out.println("email is not registered or vehicle with the given number is not present in the agency");
          }
    }
 
   //function to show rented vehicles
   public void showRentedVehicles(){
    if (!mp.isEmpty()){
      System.out.println("These are the rented vehicles:");
      for (Vehicle v : mp.values()) {
          v.showDetails();
      }
    }
    else{
      System.out.println("Rent some vehicles first!");
    }
 }
   
   //function to return the vehicle
    public void returning(int UniquNum){
          if(mp.containsKey(UniquNum)) {
              Vehicle v = mp.get(UniquNum);
              mp.remove(UniquNum);
              agency.put(UniquNum, v);
              Customer.rentedVehicles.remove(v);
              System.out.println("Vehicle returned successfully!");
          }
    }

    //calculating total cost of the rented vehicles
    public int CalculateRentalCost(int days){
        int totalCost=0;
        for(Vehicle v : mp.values()){
            totalCost += v.RentalCost(days);
        }
        return totalCost;
    }
}

/**
 * VehicleRentalSystem
 */
public class VehicleRentSystems{

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RentalAgency r = new RentalAgency();
        
       


        boolean flag = true;
        do {
            System.out.println("***********WELCOME TO VEHICLE RENTAL SYSTEM*****************");
            System.out.println("1. Add vehicles to the agency\n2. Register yourself\n3.Show available vehicles list\n4.Rent a vehicle\n5.Show total Rent\n6.Show rented vehicle\n7.Return a vehicle\n8.Exit");
            System.out.print("Enter your choice ");
            int ch = sc.nextInt();
            switch (ch) {
                case 1:
                    System.out.println("1. Add a Car\n2.Add other vehicle");
                    System.out.print("Enter the choice ");
                    int choice = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter the company: ");
                    String comp = sc.nextLine();
                    System.out.print("Enter the model: ");
                    String model = sc.nextLine();
                    System.out.print("Enter the year: ");
                    int year = sc.nextInt();
                    System.out.print("Enter the number seats: ");
                    int seats = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter the fuel type: ");
                    String fuel = sc.nextLine();
                    System.out.print("Enter the Rent per day: ");
                    int rent = sc.nextInt();
                    System.out.print("Enter the vehicle number: ");
                    int vnum = sc.nextInt();
                    switch (choice) {
                        case 1:
                            Car c =new Car(comp, model, year, rent, seats, fuel, vnum);
                            r.AddVehicles(c);
                            break;
                        case 2:
                            OtherVehicles o =new OtherVehicles(comp, model, year, rent, seats, fuel, vnum);
                            r.AddVehicles(o);
                            break;
                        default:
                        System.out.println("Invalid Input!!");
                            break;
                       }
                    break;
                case 2:
                    sc.nextLine();
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter your email: ");
                    String email = sc.nextLine();
                    Customer c1 = new Customer(name,email);
                    c1.registerCustomer(c1);
                    break;
                case 3:
                    r.showAvailableVehicles();
                    break;
                case 4:
                    sc.nextLine();
                    System.out.print("Enter your email: ");
                    String e = sc.nextLine();
                    System.out.print("Enter the vehicle number you want to rent ");
                    int vn= sc.nextInt();
                    r.rentingVehicle(vn, e);
                    break;            
                case 5:  
                   System.out.print("Enter the number of days you want to rent the vehicles: "); 
                   int d= sc.nextInt() ;
                   System.out.println("Total rental cost is: "+ r.CalculateRentalCost(d));
                case 6:
                   
                   break;
                case 7:
                  System.out.print("Enter the vehicle number: ");
                  int i = sc.nextInt();
                  r.returning(i);
                  break;
                case 8:
                   flag=false;
                  System.out.println("Exiting...");
                default:
                    break;
             }
        } while (flag);

        sc.close();
    }
}
