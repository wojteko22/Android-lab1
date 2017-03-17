package pl.edu.pwr.wojciech.okonski.lab1.lab1;

public class CountBMIforKgM implements ICountBMI {
    private float MINIMAL_MASS = 10f;
    private float MAXIMAL_MASS = 250f;
    private float MINIMAL_HEIGHT = 0.5f;
    private float MAXIMAL_HEIGHT = 2.5f;

    @Override
    public boolean isMassValid(float mass) {
        return mass > MINIMAL_MASS && mass < MAXIMAL_MASS;
    }

    @Override
    public boolean isHeightValid(float height) {
        return height > MINIMAL_HEIGHT && height < MAXIMAL_HEIGHT;
    }

    @Override
    public float calculateBMI(float mass, float height) {
        if (!isMassValid(mass) || !isHeightValid(height))
            throw new IllegalArgumentException();
        return mass / (height * height);
    }
}
