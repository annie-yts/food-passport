package Entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Passport {
    private final String userId;
    private final List<DishStamp> stamps;
    private final List<BucketListItem> bucketList;

    public Passport(String userId) {
        this.userId = userId;
        this.stamps = new ArrayList<>();
        this.bucketList = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public List<DishStamp> getStamps() {
        return stamps;
    }

    public List<BucketListItem> getBucketList() {
        return bucketList;
    }

    public void addStamp(DishStamp stamp) {
        stamps.add(stamp);
    }

    public boolean hasTried(String isoCode) {
        return !stampsFor(isoCode).isEmpty();
    }

    public List<DishStamp> stampsFor(String isoCode) {
        List<DishStamp> result = new ArrayList<>();
        for (DishStamp stamp : stamps) {
            if (stamp.getCountry().equals(isoCode)) {
                result.add(stamp);
            }
        }
        return result;
    }

    public Set<String> countriesTried() {
        Set<String> result = new HashSet<>();
        for (DishStamp stamp : stamps) {
            result.add(stamp.getCountry());
        }
        return result;
    }

    public void addToBucketList(BucketListItem item) {
        bucketList.add(item);
    }

    public void removeFromBucketList(String itemId) {
        bucketList.removeIf(item -> item.getId().equals(itemId));
    }
}