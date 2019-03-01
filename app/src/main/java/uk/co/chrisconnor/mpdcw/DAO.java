package uk.co.chrisconnor.mpdcw;

import java.util.List;

/**
 * Created by Chris Connor 25th February 2019
 */

/**
 * Generic interface for DAO
 * @param <T>
 */
public interface DAO<T> {

    /**
     * Return list of all entities
     * @return List of entities
     */
    List<T> getAll();

    /**
     * Save entity
     * @param t entity to save
     */
    void save(T t);

    /**
     * Update the entity
     * @param t entity to update
     * @param params any additional parameters
     */
    void update(T t, String[] params);

    /**
     * Delete the passed entity
     * @param t Entity to delete
     */
    void delete(T t);

}
