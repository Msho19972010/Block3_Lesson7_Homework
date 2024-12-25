package org.db.students;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

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
     * This method returns students' surnames,
     * who are between the first and the second transmitted surnames
     * @return set
     */
    public Set<String> getStudentsBySurnames(String surname1, String surname2) {

        return new HashSet<>(surnamesTreeMap.subMap(surname1, surname2).keySet());
    }
}
