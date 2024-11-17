# The "XML" and "SAX" of a shop 🛒

# XML Order Management System

This application provides functionalities for managing and processing orders in XML format using DOM and SAX parsers. Below is a brief overview of the main functionalities and their implementations.

## Table of Contents

- [createDOM](#createdom)
- [CrearElement](#crearelement)
- [readXML](#readxml)
- [readSAX](#readsax)
- [GestioContingut](#gestiocontingut)

---

## createDOM

### Description
Generates an XML file from a list of `Encarrec` (order) objects.

### Key Steps
````
1. Prompts the user to specify a name for the generated XML file.
2. Initializes an XML document using `DocumentBuilder`.
3. Iterates through the list of `Encarrec` objects:
   - Creates XML elements for each order and its attributes.
   - Embeds associated articles as child elements under the order.
4. Writes the document to a file using `Transformer`.
````

## CrearElement

### Description
Utility method to create and append an XML element to the document.

### Parameters
- `infoEncarrec`: Name of the XML element.
- `valor`: Value to be stored within the element.
- `arrel`: The parent element to which this element will be appended.
- `document`: The XML document object.


## readXML

### Description

Parses an XML file using the DOM parser and prints the details of all orders and their associated articles.

### Key Steps

Prompts the user to provide the path to the XML file.
Uses DocumentBuilder to parse the file and load it into a DOM structure.

### Extracts and prints:
1. Order attributes like id, clientName, clientPhone, etc.
2. Article details such as quantity, unit, name, and price.


## readSAX

### Description
Processes an XML file using the SAX parser and filters orders by the client's name if specified.

### Key Steps

Prompts the user for the XML file path and an optional client name filter.
Sets up the GestioContingut handler for SAX parsing.

Parses the XML file and:
If a client name is specified, only orders matching the name are displayed.
If no filter is specified, all orders are displayed.

## GestioContingut

### Description

Custom SAX handler class for processing order data.

### Key Features

1. Starts and ends the document and elements.
2. Processes and stores content for each order and article.
3. Applies the client filter logic, ensuring only relevant data is displayed.