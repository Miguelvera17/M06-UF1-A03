import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*; 
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class Fitxer {
    private static String a = "";
    public static void createAlbaran(ArrayList<Encarrec> encarrecs) throws IOException{    //Method to create a delivery note
        String fileName = "C:\\Users\\migue\\Desktop\\DAM2\\M06\\M06-UF1-A03\\" + "encarrecs_" + System.currentTimeMillis() + ".txt";//select your path
        File file = new File(fileName);
        try (BufferedWriter line = new BufferedWriter(new FileWriter(file))) {
            for (Encarrec encarrec : encarrecs) {
                line.write("Encarrec:  " + encarrec.id + "\n" +
            "Client's name:  " + encarrec.name + "\n" +
            "Client's phone: " + encarrec.phone + "\n" +
            "Order's date:   " + encarrec.data + "\n" +
            "Total price:   " + encarrec.priceTotal + "\n" +                    // Write the client's information  
            "\nQuantity       Units     Article     Price\n"  +
            "============= ========= ========= =========");
                for (Article art : encarrec.articles) {                         // Read all the articles of the client
                    a = art.toString(); 
                    line.write(a);                                              // Write the list of articles                                                
                }
                line.newLine();
                line.newLine();
            }
            line.close();
            System.out.println("\nDocument created successfully");          // The document is created
        } catch (FileNotFoundException e) {
            System.out.println("\nFAIL, documento no created");             // The document isn't created
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createCSV(ArrayList<Encarrec> encarrecs) throws IOException{                                                                 //Method to create a file in CSV
        String fileName = "C:\\Users\\migue\\Desktop\\DAM2\\M06\\M06-UF1-A03\\" + "encarrecs" + System.currentTimeMillis() + ".csv";          //select your path
        File file = new File(fileName);
        try (BufferedWriter line = new BufferedWriter(new FileWriter(file))) {
            for (Encarrec encarrec : encarrecs) {
                line.write(encarrec.id + ";" + encarrec.name + ";" + encarrec.phone + ";" + encarrec.data + ";" + encarrec.priceTotal + ";");       // Write the client's information
                for (Article art : encarrec.articles) {                         // Read all the articles of the client
                    line.write(Article.toCSV(art) + ";");                   // Accumulate 
                }
                line.newLine();                                                 // Write the line                               
            }                                                 
            line.close();                                                     // Close the line
            System.out.println("\nDocument created successfully");          // The document is created
        } catch (FileNotFoundException e) {
            System.out.println("\nFAIL, documento no created");             // The document isn't created
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createBinari(ArrayList<Encarrec> encarrecs) throws IOException{     //Method to create a file in binary
        String fileName = "C:\\Users\\migue\\Desktop\\DAM2\\M06\\M06-UF1-A03\\" + "encarrecs" + System.currentTimeMillis() + ".dat";//select your path
        File file = new File(fileName);
        try (FileOutputStream fileStr1 = new FileOutputStream(file)) {
            DataOutputStream line = new DataOutputStream(fileStr1);
            for (Encarrec encarrec : encarrecs) {
                line.writeInt(encarrec.id);                                  // Write the name of the order (int)
                line.writeUTF(encarrec.name);                                // Write the name of the client (String)
                line.writeUTF(encarrec.phone);                               // Write the phone of the client (String)
                line.writeUTF(encarrec.data);                                // Write the date (String)
                line.writeFloat(encarrec.priceTotal);                        // Write the pricetotal (float)
                for (Article art : encarrec.articles) {
                    line.writeFloat(art.getQuantity());                         // Write the quantity (Float)
                    line.writeUTF(art.getUnit());                               // Write the units (String)
                    line.writeUTF(art.getName());                               // Write the name of the article (String)
                    line.writeFloat(art.getPrice());                            // Write the price of the article (float)             
                }
                line.writeFloat(-1.0f);
            }
            line.close();
            fileStr1.close();
            System.out.println("\nDocument created successfully");      // The document is created
        } catch (FileNotFoundException e) {
            System.out.println("\nFAIL, documento no created");         // The document isn't created
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createDOM(ArrayList<Encarrec> encarrecs) throws IOException {
        System.out.println("\nIndicate the name");
        System.out.print("\n----> ");
        String name = Entrada.readLine();
        System.out.print("\n");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument (null,"encarrecs", null);
            document.setXmlVersion("1.0");
            for ( Encarrec encarrec: encarrecs){
        
                Element arrel = document.createElement ("encarrec");
                arrel.setAttribute("id",Integer.toString(encarrec.id));
                document.getDocumentElement().appendChild(arrel);

                CrearElement ("clientName",encarrec.name, arrel, document);
                CrearElement ("clientPhone",encarrec.phone, arrel, document);
                CrearElement ("orderdate", encarrec.data, arrel, document);
                CrearElement ("totalPrice", Float.toString(encarrec.priceTotal),arrel, document);
                Element articlesElement = document.createElement("Articles");
                arrel.appendChild(articlesElement);

                for (Article article : encarrec.articles) {
                    Element articleElement = document.createElement("Article");
                    articlesElement.appendChild(articleElement);

                    CrearElement("Quantity", Float.toString(article.getQuantity()), articleElement, document);
                    CrearElement("Unit", article.getUnit(), articleElement, document);
                    CrearElement("Name", article.getName(), articleElement, document);
                    CrearElement("Price", Float.toString(article.getPrice()), articleElement, document);
                }
            }
        
            Source source = new DOMSource (document);
            Result result = new StreamResult (new FileWriter(name + ".xml"));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "5");
            transformer.transform (source, result);
            System.out.println("XML generated");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e ) { 
            System.err.println ("Error: " + e);
        }  
    }

    public static void readXML(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        System.out.print("\nIndicate the path: ");                           // Ask for the path
        String filePath = Entrada.readLine();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filePath);

            NodeList encarrecs = document.getElementsByTagName("encarrec");

            for (int i = 0; i < encarrecs.getLength(); i++) {
                Node encargo = encarrecs.item(i);

                if (encargo.getNodeType() == Node.ELEMENT_NODE){

                    Element element = (Element) encargo;
                    System.out.println("ENCARGO N-" + element.getAttribute("id"));
                    System.out.println("Id del encargo: " + element.getAttribute("id"));
                    System.out.println("Nombre del cliente: " + element.getElementsByTagName("clientName").item(0).getTextContent());
                    System.out.println("Teléfono del cliente: " + element.getElementsByTagName("clientPhone").item(0).getTextContent());
                    System.out.println("Fecha del encargo: " + element.getElementsByTagName("orderdate").item(0).getTextContent());
                    System.out.printf("%-15s %-15s %-15s %-15s%n", "Cantidad", "Unidades", "Artículo", "precio");
                    System.out.printf("%-15s %-15s %-15s %-15s%n", "===============", "===============", "===============", "===============");
                    NodeList articles = element.getElementsByTagName("Articles");
                    for(int x = 0; x < articles.getLength(); x++){
                        Node articulo = articles.item(x);
                        if(articulo.getNodeType() == Node.ELEMENT_NODE){
                            Element element2 = (Element) articulo;
                            System.out.printf("%-15s %-15s %-15s %-15s%n", element2.getElementsByTagName("Quantity").item(0).getTextContent(),  element2.getElementsByTagName("Unit").item(0).getTextContent(),element2.getElementsByTagName("Name").item(0).getTextContent(), element2.getElementsByTagName("Price").item(0).getTextContent());
                        }
                    }
                    System.out.printf("%-15s %-15s %-15s %-15s", "===============", "===============", "===============", "===============");
                    System.out.println("\nPrecio total del encargo: " + element.getElementsByTagName("totalPrice").item(0).getTextContent()+ "€");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        
    public static void CrearElement (String infoEncarrec, String valor, Element arrel, Document document) {
        Element elem = document.createElement (infoEncarrec);
        Text text = document.createTextNode(valor);
        arrel.appendChild (elem);
        elem.appendChild (text);
    }

    public static void readBinari() {                                           
        System.out.print("\nIndicate the path: ");                           // Ask for the path
        String filePath = Entrada.readLine();
        try (FileInputStream f1 = new FileInputStream(filePath)) {
            DataInputStream f = new DataInputStream(f1);
            while (f.available() > 0) {  // Mientras haya datos en el archivo

                // Leer la información básica del encargo
                System.out.println("\nEncarrec id: " + f.readInt());
                System.out.println("Client's name: " + f.readUTF());
                System.out.println("Client's phone: " + f.readUTF());
                System.out.println("Order's date: " + f.readUTF());
                System.out.println("Price total: " + f.readFloat());
    
                System.out.println("\nQuantity       Units     Article   Price\n" + 
                                   "===========   =========  ========= ======== ");
    
                // Leer cada artículo hasta encontrar la marca de fin de artículos
                while (f.available() > 0) {  
                    float quantity = f.readFloat();
    
                    // Verificar si quantity tiene el valor especial de fin de artículos (ej., -1)
                    if (quantity == -1.0f) {
                        break;  // Salir del bucle de artículos
                    }
    
                    // Leer el resto de los datos del artículo
                    String units = f.readUTF();
                    String article = f.readUTF();
                    float price = f.readFloat();
    
                    // Mostrar la información del artículo
                    System.out.println(String.format(Locale.US, "%-13.1f %-10s %-10s %-10.2f", quantity, units, article, price));
                }
            }
            f.close();
            f1.close();
        } catch (FileNotFoundException e) {
            System.out.println("\nFAIL, path not correct");         // The document is not found
            
        } 
        catch (EOFException e) {
            System.out.println("\nFAIL, incorrect document");       // Not correct extension
        }
        catch (IOException e) {
                e.printStackTrace();
        }
    }

    public static void readCSV () {
        // Ask for the path of the document you want to read
        System.out.print("\nIndicate the path: ");                          // Ask for the path
        String filePath = Entrada.readLine();
        if (filePath.endsWith(".csv")) {                               // Check the suffix
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;

                // Read the lines until it finds null 
                while ((line = br.readLine()) != null) {

                    // Separate the values by ";"
                    String[] values = line.split(";");
                    String id = values[0];
                    String clientName = values[1];
                    String clientPhone = values[2];
                    String orderDate = values[3];
                    String priceTotal = values[4];
                    System.out.println("\nEncarrec id:  " + id);
                    System.out.println("\nClient's name:  " + clientName);      // Print name of the client (String)
                    System.out.println("Client's phone: " + clientPhone);       // Print phone of the client (String)
                    System.out.println("Order's date:   " + orderDate);         // Print date (String)
                    System.out.println("Price total:   " + priceTotal + "\n");
                    System.out.println(String.format("%-12s %-10s %-12s %-12s", "Quantity", "Units", "Article", "Price"));
                    System.out.println(String.format("=========== ========== =========== ==========="));
                    for (int i = 5; i < values.length; i += 4) {
                        String quantity = values[i+1];
                        String units = values[i + 2];
                        String article = values[i];
                        String price = values[i + 3];
                        System.out.println(String.format(Locale.US, "%-12s %-10s %-12s %-12s", quantity, units, article, price));    // Print the list of article (Flaot, String, String)
                    }
                }
                br.close();
            } catch (FileNotFoundException e) {
                System.out.println("\nFAIL, path not correct");         // The document is not found
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\nFAIL, incorrect document");           // Not correct extension
        }
    }

    public static void selectDocument(ArrayList<Encarrec> encarrecs) throws IOException{      // Method to create a document
        while (true) {
            System.out.println("\nWhich document do you want?:\n" + 
                            "a) Albaran  [a] \n" + 
                            "b) Binary   [b]\n" +
                            "c) CSV \t    [c] \n" +
                            "d) XML \t    [x]");
            System.out.print("\n----> ");
            String select = Entrada.readLine();
            if (select.equals("a")) {                           // to create a .txt file
                createAlbaran(encarrecs);
                
                break;
            }
            if (select.equals("b")) {                           // to create a binary file
                Fitxer.createBinari(encarrecs);
                
                break;
            }
            if (select.equals("c")) {                           // to create a .csv file
                Fitxer.createCSV(encarrecs);

                break;
            }
            if (select.equals("x")) {                           // to create a .csv file
                Fitxer.createDOM(encarrecs);

                break;
            }
            if(!select.equals("a") && !select.equals("b") && !select.equals("c") && !select.equals("x")) {
                System.out.println("\n=======================");
                System.out.println("Invalid option\nChoose a correct option");
                System.out.println("======================="); 
            }
        }
    }

    public static void selectReadDocument() throws IOException {            // Method to choose which document you want to read
        System.out.println("\nWhich document do you want read?:\n" +  
                                    "a) Binary\t[b]\n" +
                                    "b) CSV \t\t[c] \n" +
                                    "c) Deserializar [d] \n" +
                                    "d) XML \t    [m] \n" +
                                    "e) Exit\t\t[x]");
        System.out.print("\n----> ");
        String ans = Entrada.readLine();
                
        if (ans.equals("b")) {                                      // [b] to read a binary file
            Fitxer.readBinari();
        }
        if (ans.equals("c")) {                                      // [c] to read a .CSV file
            Fitxer.readCSV();
        }
        if (ans.equals("d")) {                                      // [d] deserailizar
            Encarrec.Deserializador();
        }
        if   (ans.equals("m")){                                     //[m] to read a .xml file
            Fitxer.readXML();
        }
        if (ans.equals("x")) {                                      // [x] to exit
        }
        if (!ans.equals("b") && !ans.equals("c") && !ans.equals("x") && !ans.equals("d") && !ans.equals("m")) {
            System.out.println("\n=======================");
            System.out.println("Invalid option\nChoose a correct option");
            System.out.println("=======================");
        }
    }
}
