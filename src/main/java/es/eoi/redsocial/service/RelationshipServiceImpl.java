package es.eoi.redsocial.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.eoi.redsocial.entity.Relationship;
import es.eoi.redsocial.entity.User;
import es.eoi.redsocial.enums.RelationEnum;
import es.eoi.redsocial.repository.RelationshipRepository;

@Service
public class RelationshipServiceImpl implements RelationshipService {

	@Autowired
	private RelationshipRepository repository;

	@Override
	@Transactional
	public Relationship saveRelationship(User user1, User user2) {

		List<Relationship> listRelation = new ArrayList<>();

		user1.getRelationshipFollowing().stream().forEach(r -> listRelation.add(r));
		user2.getRelationshipFollowing().stream().forEach(r1 -> listRelation.add(r1));
		Relationship relation = new Relationship();
		if (listRelation.stream()
				.anyMatch(r -> r.getUserFollow().equals(user2) || r.getUserFollow().equals(user1) == true)) {
			return null;
		} else {
			relation.setUserFollowing(user1);
			relation.setUserFollow(user2);
			relation.setState(RelationEnum.PENDING);
			repository.save(relation);
		}

		return relation;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Relationship> findRelationshipById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Relationship> getAllRelationships() {
		return repository.findAll();
	}

	@Override
	@Transactional
	public Relationship updateRelationship(Relationship relationship) {
		relationship.setState(RelationEnum.FRIEND);
		return repository.save(relationship);

	}

	@Override
	@Transactional
	public void deleteRelationshipById(Long id) {
		repository.deleteById(id);
	}
	
	@Override
	@Transactional
	public List<User> findBestFriendlyUsers() {
		List<User> allUsers = repository.findBestFriendlyUsers();
		List<User> bestUsers = new ArrayList<>();
		Integer count = 0;
		
		for (User user : allUsers) {
			if (count != 3) {
				bestUsers.add(user);
				count++;
			}
		}
		
		return bestUsers;
	}

}
