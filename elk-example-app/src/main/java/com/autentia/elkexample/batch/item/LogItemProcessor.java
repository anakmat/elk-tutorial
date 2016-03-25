package com.autentia.elkexample.batch.item;

import com.autentia.elkexample.domain.Item;
import java.util.Random;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;

public class LogItemProcessor implements ItemProcessor<Item, Item> {
    private static final Log LOG = LogFactory.getLog(LogItemProcessor.class);

    private Random random;

    public LogItemProcessor() {
        random = new Random();
    }

    @Override
    public Item process(Item item) throws Exception {
        LOG.info("Processed item: " + item.toString());
        float value = random.nextFloat();
        if (value < 0.5) {
            LOG.error("This item is ugly because " + value + " < 0.5! " + item.toString());
        }
        return item;
    }

}
