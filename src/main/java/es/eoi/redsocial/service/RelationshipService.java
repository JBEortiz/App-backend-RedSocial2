package es.eoi.redsocial.service;

import java.util.List;
import java.util.Optional;

import es.eoi.redsocial.entity.Relationship;
import es.eoi.redsocial.entity.User;

public interface RelationshipService {
	
	 Relationship saveRelationship(User user1, User user2);

     Optional<Relationship> findRelationshipById(Long id);

     List<Relationship> getAllRelationships ();

     Relationship updateRelationship(Relationship relationship);

     void deleteRelationshipById(Long id);
     
     List<User> findBestFriendlyUsers();
}
