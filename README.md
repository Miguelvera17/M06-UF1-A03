# The "XML" and "SAX" of a shop 🛒

# XML Order Management System

This application provides functionalities for managing and processing orders in XML format using DOM and SAX parsers. Below is a brief overview of the main functionalities and their implementations.

## Table of Contents

- [createDOM](#createdom)
- [CrearElement](#crearelement)
- [readXML](#readxml)
- [readSAX](#readsax)

---

## createDOM

### Description
Generates an XML file from a list of `Encarrec` (order) objects.

### Key Steps
1. Prompts the user to specify a name for the generated XML file.
2. Initializes an XML document using `DocumentBuilder`.
3. Iterates through the list of `Encarrec` objects:
   - Creates XML elements for each order and its attributes.
   - Embeds associated articles as child elements under the order.
4. Writes the document to a file using `Transformer`.

### Output
- An XML file containing the provided orders and their details.

---

## CrearElement

### Description
Utility method to create and append an XML element to the document.

### Parameters
- `infoEncarrec`: Name of the XML element.
- `valor`: Value to be stored within the element.
- `arrel`: The parent element to which this element will be appended.
- `document`: The XML document object.

### Example
```java
CrearElement("clientName", encarrec.name, arrel, document);
