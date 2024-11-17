import java.io.*;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Store {
    
    public static Article article;  // declare an article
    public static int num = 1;      // count the number of articles
    public static ArrayList<Encarrec> encarreclist = new ArrayList<>();
    public static Encarrec Enc;
    
    public static void main (String[] args) throws IOException, FileNotFoundException, IOException, SAXException, ParserConfigurationException {
        System.out.print("Welcome to my store!!\n" +
                            "=====================\n" + 
                            "\n" +
                            "What would you like to do? \n" + 
                            "a) Generate \"Encarrec\"  [n] \n" + 
                            "b) Show \"Encarrec\" \t[s] \n" +
                            "c) Modify \"Encarrec\" \t[m] \n" +
                            "d) Exit\t\t\t[x]\n" + 
                            "\n" +
                            "----> ");
        String action = Entrada.readLine();
        while (!action.equals("x")) {                                   // Type [x] to exit
            if (action.equals("n")) {                                   // Type [n] to generate a new order
                boolean more = true;
                Client client = new Client();
                ArrayList<Article> articles = new ArrayList<>();
                int id = 1;
                while (more) {
                    client = Client.createClient();                                             // Create a new client
                    articles = Article.createArticle(client);                                  // Create a list of articles for the client
                    encarreclist = Encarrec.createEncargo(id,articles,client,Encarrec.totalPrice(articles));
                    System.out.println("\nWould you like to add one more \"encarrec\"? [y] [n]");
                    System.out.print("----> " );
                    String answer = Entrada.readLine();
                    if (answer.equals("n")) {
                        break;
                    }
                    id++;
                }
                
                Encarrec.Serializador();
                
                Encarrec.fileRandom();
                Fitxer.selectDocument(encarreclist);                                 // Select the document you want to create
                encarreclist.clear();
            }
            if (action.equals("s")) {                                       // Type [s] to show a previus order
                Fitxer.selectReadDocument();                                         // Select the document you want to read
            }
            if (action.equals("m")) {                                       // Type [m] to show a previus order
                Encarrec.modifyRandom();
            }
            if (!action.equals("s") && !action.equals("n") && !action.equals("m")) {  // If the option is not correct, it will ask again to choose the correct one.
                System.out.println("\n=======================");
                System.out.println("Invalid option\nChoose a correct option");
                System.out.println("=======================");
                
            }
            System.out.print("\n" + 
                            "What would you like to do? \n" + 
                            "a) Generate \"Encarrec\"  [n] \n" + 
                            "b) Show \"Encarrec\" \t[s] \n" +
                            "c) Modify \"Encarrec\" \t[m] \n" +
                            "d) Exit\t\t\t[x]\n" + 
                            "\n" +
                            "----> ");
            action = Entrada.readLine();
        }
        System.out.println("\nBye\n");
    }
}