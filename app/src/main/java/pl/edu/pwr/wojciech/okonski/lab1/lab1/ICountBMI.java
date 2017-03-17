package pl.edu.pwr.wojciech.okonski.lab1.lab1;

public interface ICountBMI {
    boolean isMassValid(float mass);
    boolean isHeightValid(float height);
    float calculateBMI(float mass, float height);
}
