package lw01.unguided;

public abstract class WashService implements Billable {
    private String id;
    private int days;

    public WashService(String id, int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Days must be positive.");
        }
        this.id = id;
        this.days = days;
    }

    public String getId() {
        return id;
    }

    public int getDays() {
        return days;
    }

    @Override
    public abstract int calculateCharge();

    public int calculateCharge(int units) {
        if (units <= 0) {
            throw new IllegalArgumentException("Units must be positive.");
        }
        return units * calculateCharge();
    }

    public String label() {
        return "Service";
    }

    public String summary() {
        return id + " | " + label() + " | " + calculateCharge();
    }
}
