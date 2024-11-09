import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.io.Serializable;


public class Encarrec implements Serializable {

    private static final long serialVersionUID = 1L;
    public int id;
    public String name;
    public String phone;
    public String data;
    public float priceTotal;
    ArrayList<Article> articles;
    public static ArrayList<Encarrec> encarrecs = new ArrayList<>();

    public Encarrec() {}

    public Encarrec(int id, String name, String phone, String data, ArrayList<Article> articles,float priceTotal ) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.data = data;
        this.articles = articles;
        this.priceTotal = priceTotal;
    }

    public static ArrayList<Encarrec> createEncargo(int id, ArrayList<Article> articles, Client client, float priceTotal) {
        Encarrec encarrec = new Encarrec(id, client.getName(), client.getPhone(), client.getDate(), articles, priceTotal);
        encarrecs.add(encarrec);
        return encarrecs;
    }

    public static float totalPrice(ArrayList<Article> articles) {
        float total = 0;
        for (int i = 0; i < articles.size(); i++) {
            total = total + articles.get(i).getPrice();
        }
        return total;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Encarrec %d [", this.id));
        sb.append("Name=" + this.name + ", " + "Phone=" + this.phone + ", " + "Date=" + this.data + ", " + "Articles: ");
        for (Article article : this.articles) {
            sb.append("{ Quantity=" + article.getQuantity() + ", " + "Unit=" + article.getUnit() + ", " + "articleName=" + article.getName() + ", " + "Price=" + article.getPrice() + " }, ");
        }
        sb.append("Total=" + this.priceTotal + "]");

        return sb.toString();
    }

    public static void Serializador() {
		
	try (ObjectOutputStream serializador = new ObjectOutputStream(new FileOutputStream("C:\\Users\\migue\\Desktop\\DAM2\\M06\\M06-UF1-A03\\" + "Serial_" + System.currentTimeMillis() + ".dat"))) {
        serializador.writeObject(encarrecs);
        System.out.println("\n\"Serializado\" file generated");
    } catch (IOException ioe) { 
        System.out.print(ioe.getMessage());
    }
    }

    public static void Deserializador() throws IOException {
        System.out.print("\nPut the path: ");
        String path = Entrada.readLine();
    
        try (ObjectInputStream deserializador = new ObjectInputStream(new FileInputStream(path))) {
            encarrecs.clear();                                  // We clear the list before deserializing
            ArrayList<Encarrec> listaDeserializada = (ArrayList<Encarrec>) deserializador.readObject();
            encarrecs.addAll(listaDeserializada);
            
            for (Encarrec e : encarrecs) {                      // Display deserialized orders
                System.out.println("\n" + e);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void fileRandom() throws IOException {
        
        try (RandomAccessFile raw1 = new RandomAccessFile("Random_" + System.currentTimeMillis() + ".dat", "rw");) {
            // Create a new file with a name based on the current time
            for (Encarrec encarrec : encarrecs) {
                int longRecord = 0;
                raw1.writeInt(encarrec.id);                                     // Write the order ID (int -> 4 bytes)
                longRecord += Integer.BYTES;
        
                StringBuffer nameBuffer = new StringBuffer(encarrec.name);      // Enter the client name (Fixed length string -> 20 chars = 40 bytes)
                nameBuffer.setLength(20);                             
                raw1.writeChars(nameBuffer.toString());
                longRecord += nameBuffer.length();                              // 2 bytes by char
        
                StringBuffer phoneBuffer = new StringBuffer(encarrec.phone);    // Write the customer's phone number (Fixed length string -> 20 chars = 40 bytes)
                phoneBuffer.setLength(20);
                raw1.writeChars(phoneBuffer.toString());
                longRecord += phoneBuffer.length();
        
                StringBuffer dateBuffer = new StringBuffer(encarrec.data);      // Write the date of the order (Fixed length string -> 20 chars = 40 bytes)
                dateBuffer.setLength(20);
                raw1.writeChars(dateBuffer.toString());
                longRecord += dateBuffer.length();

                raw1.writeFloat(encarrec.priceTotal);                           // Write the quantity of the item (float -> 4 bytes)
                    longRecord += Float.BYTES;
        
                raw1.writeInt(encarrec.articles.size());                        // Write the number of items (int -> 4 bytes)
                longRecord += Integer.BYTES;
        
                for (Article art : encarrec.articles) {                         // Writing the articles
                    
                    raw1.writeFloat(art.getQuantity());                         // Write the quantity of the item (float -> 4 bytes)
                    longRecord += Float.BYTES;
        
                    StringBuffer unitBuffer = new StringBuffer(art.getUnit());  // Write the item unit (Fixed length String -> 5 chars = 10 bytes)
                    unitBuffer.setLength(20);
                    raw1.writeChars(unitBuffer.toString());
                    longRecord += unitBuffer.length();
        
                    
                    StringBuffer articleNameBuffer = new StringBuffer(art.getName());   // Write the name of the article (Fixed length string -> 20 chars = 40 bytes)
                    articleNameBuffer.setLength(20);
                    raw1.writeChars(articleNameBuffer.toString());
                    longRecord += articleNameBuffer.length();
        
                    raw1.writeFloat(art.getPrice());                            // Write the price of the item (float -> 4 bytes)
                    longRecord += Float.BYTES;
                }
                raw1.writeInt(longRecord);
            }
            raw1.close();
            System.out.println("\"Random file generated\"");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void modifyRandom() {
        System.out.print("\nIntroduce path: ");
        String path = Entrada.readLine();                                                       // Get the file path from the user.
        try (RandomAccessFile raw1 = new RandomAccessFile(path, "rw")) {                   // Open the file in read-write mode.
            System.out.print("\nIntroduce id: ");
            String idF = Entrada.readLine();                                                    // Get the ID to modify from the user.
            boolean found = false;
            
            // Traverse the file until end
            while (raw1.getFilePointer() < raw1.length()) {  
                long position = raw1.getFilePointer();                                          // Store the position of the current record.
                int id = raw1.readInt();                                                        // Read the integer ID from the file.
                
                if (id == Integer.parseInt(idF)) {                                              // Check if the current ID matches the input ID.
                    found = true;
                    System.out.println("\nWhat would you like to modify? Phone [p] | Date [d]");
                    System.out.print("\n----> ");
                    String asw = Entrada.readLine();                                            // Prompt user for field to modify.
                    
                    if (asw.equals("p")) {                                              // If modifying phone.
                        raw1.seek(position + Integer.BYTES + 20 + 20);                          // Skip ID, name, and other fields to reach phone.
                        System.out.print("\nIntroduce new phone: ");
                        String nouTelefon = Entrada.readLine();                                 // Get the new phone number.
                        StringBuilder telefonBuilder = new StringBuilder(nouTelefon);
                        telefonBuilder.setLength(20);                                  // Ensure the phone field has a fixed length of 20.
                        raw1.writeChars(telefonBuilder.toString());                             // Write the new phone to the file.
                        System.out.println("\nPhone updated");
                        break;
                    }
                    
                    if (asw.equals("d")) {  // If modifying date.
                        raw1.seek(position + Integer.BYTES + 20 + 20 + 20 + 20);              // Skip ID, name, phone, and other fields to reach date.
                        System.out.print("\nIntroduce new date: ");
                        String novaData = Entrada.readLine();                                 // Get the new date.
                        StringBuilder dataBuilder = new StringBuilder(novaData);
                        dataBuilder.setLength(20);                                  // Ensure the date field has a fixed length of 20.
                        raw1.writeChars(dataBuilder.toString());                              // Write the new date to the file.
                        System.out.println("\nDate updated");
                        break;
                    }
                    if (!asw.equals("p") && !asw.equals("d")) {             // Handle invalid input for field selection.
                        System.out.println("Invalid choice");
                    }
                } else {                                                                     // If ID does not match, skip to the next record.
    
                    for (int i = 0; i < 20; i++) {
                        raw1.readChar();                                                    // Skip 20 characters for name field.
                    }
                    
                    for (int i = 0; i < 20; i++) {
                        raw1.readChar();                                                    // Skip 20 characters for phone field.
                    }
                    
                    for (int i = 0; i < 20; i++) {
                        raw1.readChar();                                                    // Skip 20 characters for date field.
                    }   
                    
                    raw1.readFloat();                                                       // Skip the float field.
                    int numArticles = raw1.readInt();                                       // Read number of articles.
                    
                    for (int i = 0; i < numArticles; i++) {                                 // Loop to skip all article records.
                        for (int j = 0; j < 20; j++) {
                            raw1.readChar();                                                // Skip 20 characters for article title.
                        }
                        raw1.readFloat();                                                   // Skip article price.
                        
                        for (int j = 0; j < 20; j++) {
                            raw1.readChar();                                                // Skip 20 characters for article description.
                        }
                        raw1.readFloat();                                                   // Skip article quantity.
                    }
                    
                    raw1.readInt();                                                         // Skip trailing integer in the record.
                }
            }
    
            if (!found) {                                                                   // If ID was not found in the file.
                System.out.println("Not found ID.");
            }
    
        } catch (IOException e) {                                                           // Catch and handle IO exceptions.
            System.out.println("Error: " + e.getMessage());
        }
    }
}