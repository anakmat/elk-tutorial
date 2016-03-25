package com.autentia.elkexample.batch.item;

import com.autentia.elkexample.domain.Item;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;

public class LogItemWriter implements ItemWriter<Item> {
	private static final Log LOG = LogFactory.getLog(LogItemWriter.class);

	@Override
	public void write(List<? extends Item> items) throws Exception {
		LOG.info("Written item: " + items.toString());
	}
}
