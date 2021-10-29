package com.openkm.module.db.stuff;

import org.apache.lucene.document.Document;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LongFieldBridge implements FieldBridge {
	private static Logger log = LoggerFactory.getLogger(LongFieldBridge.class);
	@Override
	public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {
		log.info("OBJECT: ", value);
		if(value == null) {
			log.warn("NULL VALUE");
		} else {
			if(value instanceof Long){
				String str = String.valueOf(value);
				luceneOptions.addFieldToDocument(name, str, document);
			}
			else {
				log.warn("IllegalArgumentException: Support only Long");
				throw new IllegalArgumentException("Support only Long");
			}
		}
	}
}
