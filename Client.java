public class Client {

    public String name;         // name of the client
    public String phone;        // name of the client
    public String date;         // name of the client
    public Article articles;    // articles of the client

    public Client() {}

    public Client(String name, String phone, String date) {         // Constructor specific
        this.name = name;
        this.phone = phone;
        this.date = date;
    }
    
    public String getName() {                                       // Getter
        return name;
    }

    public void setName(String name) {                              // Setter
        this.name = name;
    }

    public String getPhone() {                                      // Getter
        return phone;
    }

    public void setPhone(String phone) {                            // Setter
        this.phone = phone;
    }

    public String getDate() {                                       // Getter
        return date;
    }

    public void setDate(String date) {                              // Setter
        this.date = date;
    }

    public static Client createClient() {                           // Method to create a new client
        System.out.print("\n" +
                                "======= Client information =======\n" +
                                "Name:\t\t");
                String name = Entrada.readLine();
                System.out.print("Phone:\t\t");
                String phone = Entrada.readLine();
                System.out.print("Order's date:\t");
                String date = Entrada.readLine();
                System.out.println("==================================");
                Client client = new Client(name,phone,date);
                return client;
    }
}
