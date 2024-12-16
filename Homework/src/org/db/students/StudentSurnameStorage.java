package org.db.students;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class StudentSurnameStorage {
    private static TreeMap<String, Set<Long>> surnamesTreeMap = new TreeMap<>();

    public void studentCreate(Long id, String surname) {
        Set<Long> existingIds = surnamesTreeMap.getOrDefault(surname, new HashSet<>());
        existingIds.add(id);
        surnamesTreeMap.put(surname, existingIds);
    }

    public void studentDeleted(Long id, String surname) {
        surnamesTreeMap.get(surname).remove(id);
    }

    public void studentUpdated(Long id, String oldSurname, String newSurname) {
        studentDeleted(id, oldSurname);
        studentCreate(id, newSurname);
    }

    /**
     * This method returns unique students' identifires,
     * whose surnames are less or equal of transmitted
     * @return set
     */
    public Set<String> getStudentsBySurnames(String surname1, String surname2) {

        return new HashSet<>(surnamesTreeMap.subMap(surname1, surname2).keySet());
    }
}
