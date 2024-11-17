import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class GestioContingut extends DefaultHandler {
    private Encarrec currentEncarrec;
    private Article currentArticle;
    private StringBuilder content;
    private String clientFilter;
    private boolean matchesFilter;

    public GestioContingut(String clientFilter) {
        this.clientFilter = clientFilter != null && !clientFilter.isEmpty() ? clientFilter : null;
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Inici del document XML");
        content = new StringBuilder();
        matchesFilter = false;
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Final del document XML");
    }

    @Override
    public void startElement(String uri, String nom, String nomC, Attributes attributes) throws SAXException {
        content.setLength(0); 
        if (nomC.equals("encarrec")) {
            currentEncarrec = new Encarrec();
            String id = attributes.getValue("id");
            currentEncarrec.setId(Integer.parseInt(id));
        } else if (nomC.equals("Article")) {
            currentArticle = new Article();
        }
    }
    @Override
    public void endElement(String uri, String nom, String nomC) throws SAXException {
        switch (nomC) {
            case "clientName":
                currentEncarrec.setName(content.toString());
                if (clientFilter != null && currentEncarrec.name.equalsIgnoreCase(clientFilter)) {
                    matchesFilter = true;
                }
                break;
            case "clientPhone":
                currentEncarrec.setPhone(content.toString());
                break;
            case "orderdate":
                currentEncarrec.setData(content.toString());
                break;
            case "totalPrice":
                currentEncarrec.setPriceTotal(Float.parseFloat(content.toString()));
                break;
            case "Quantity":
                currentArticle.setQuantity(Float.parseFloat(content.toString()));
                break;
            case "Unit":
                currentArticle.setUnit(content.toString());
                break;
            case "Name":
                currentArticle.setName(content.toString());
                break;
            case "Price":
                currentArticle.setPrice(Float.parseFloat(content.toString()));
                currentEncarrec.addArticle(currentArticle);
                break;
            case "encarrec":
                if (clientFilter == null || matchesFilter) {
                    System.out.println(currentEncarrec);
                }
                matchesFilter = false; 
                break;
        }
    }

    @Override
    public void characters(char[] ch, int inicio, int length) throws SAXException {
        content.append(ch, inicio, length);
    }
}