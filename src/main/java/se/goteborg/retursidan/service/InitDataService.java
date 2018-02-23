package se.goteborg.retursidan.service;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import se.goteborg.retursidan.model.entity.Category;
import se.goteborg.retursidan.model.entity.Unit;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class InitDataService {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	private ModelService modelService;

	private Category getCategory(XPath xpath, Node node) throws XPathExpressionException {
		Category category = new Category();
		String title = (String)xpath.evaluate("title", node, XPathConstants.STRING);
		category.setTitle(title);
		return category;
	}

	public void loadInitialData() {
		try {
			String path = this.getClass().getResource("/").getFile();
			int i = path.indexOf("WEB-INF");
			if (i != -1) {
				path = path.substring(0, i + 7) + File.separator + "initial-data-import.xml"; 
				File xmlFile = new File(path);
				if(xmlFile.exists()) {
					logger.log(Level.FINE, "Importing initial data");
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					DocumentBuilder db = dbf.newDocumentBuilder();
					Document doc = db.parse(xmlFile);
					
					XPathFactory xPathfactory = XPathFactory.newInstance();
					XPath xpath = xPathfactory.newXPath();

					// categories
					if (modelService.getCategories().size() == 0) {
						NodeList categories = (NodeList)xpath.evaluate("/import/categories/category", doc, XPathConstants.NODESET);
						for(int n = 0; n < categories.getLength(); n++) {
							Node node = categories.item(n);
							Category topCategory = getCategory(xpath, node);
							modelService.addCategory(topCategory);
							logger.log(Level.FINER, "Importing category '" + topCategory.getTitle() + "' with id = " + topCategory.getId());
							NodeList subCategories = (NodeList)xpath.evaluate("subcategories/category", node, XPathConstants.NODESET);
							for(int x = 0; x < subCategories.getLength(); x++) {
								Category subCategory = getCategory(xpath, subCategories.item(x));
								subCategory.setParent(topCategory);
								modelService.addCategory(subCategory);
								logger.log(Level.FINER, "Importing sub category '" + topCategory.getTitle() + " > " + subCategory.getTitle() + "' with id = " + subCategory.getId());
							}
						}
					}
					
					// units
					if (modelService.getUnits().size() == 0) {
						NodeList units = (NodeList)xpath.evaluate("/import/units/unit", doc, XPathConstants.NODESET);
						for(int n = 0; n < units.getLength(); n++) {
							Node node = units.item(n);
							Unit unit = new Unit();
							String name = node.getTextContent();
							unit.setName(name);
							modelService.addUnit(unit);
							logger.log(Level.FINER, "Importing unit '" + unit.getName() + "' with id = " + unit.getId());
						}
					}
				} else {
					logger.log(Level.FINE, "No initial data import file found, skipping import.");
				}
			}
		} catch(Exception e) {
			logger.log(Level.WARNING, "Could not import initial data using XML file", e);
		}	}

}