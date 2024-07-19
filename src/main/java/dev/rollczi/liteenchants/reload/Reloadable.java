package dev.rollczi.liteenchants.reload;

public interface Reloadable {

    default void init() {}

    void reload();

}
