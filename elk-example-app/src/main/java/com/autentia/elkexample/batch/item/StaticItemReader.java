package com.autentia.elkexample.batch.item;

import com.autentia.elkexample.domain.Item;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.UnexpectedInputException;

public class StaticItemReader implements ItemReader<Item> {
    private static final Log LOG = LogFactory.getLog(StaticItemReader.class);
    private static final int DEFAULT_DURATION = 2;
    private static final int SLEEP_MILLIS = 1000;

    private int index;
    private int duration;
    private List<Item> items;
    private Random random;
    private LocalDateTime startTime;

    public StaticItemReader() {
        index = 0;
        duration = DEFAULT_DURATION;
        items = new ArrayList<>();
        items.add(new Item("Item Authentic", true));
        items.add(new Item("Item Brilliant", true));
        items.add(new Item("Item Cool", true));
        items.add(new Item("Item Dreadful", true));
        items.add(new Item("Item Expected", false));
        items.add(new Item("Item Free", true));
        items.add(new Item("Item Great", true));
        items.add(new Item("Item Hypersonic", true));
        items.add(new Item("Item Joop", true));
        items.add(new Item("Item Killer", true));
        items.add(new Item("Item Lame", true));
        items.add(new Item("Item Minimal", true));
        items.add(new Item("Item Natural", true));
        items.add(new Item("Item Optimistic", true));
        random = new Random();
    }

    @Override
    public Item read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (index >= items.size()) {
            index = 0;
        }
        if (startTime == null) {
            startTime = LocalDateTime.now();
        }
        if (startTime.plusSeconds(duration).isAfter(LocalDateTime.now())) {
            float value = random.nextFloat();
            if (value < 0.10) { // 10% of items extracted generate an exception
                try {
                    throw new Exception("This is a random test exception because " + value + " < 0.10!");
                } catch (Exception e) {
                    LOG.warn("Ignoring exception...", e);
                }
            }

            Thread.sleep(SLEEP_MILLIS);
            LOG.debug("Extracting next item at: " + index);
            return items.get(index++);
        }
        return null;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
