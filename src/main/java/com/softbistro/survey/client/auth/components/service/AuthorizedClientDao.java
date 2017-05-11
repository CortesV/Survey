package com.softbistro.survey.client.auth.components.service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.client.auth.components.entities.AuthorizedClient;
import com.softbistro.survey.client.auth.components.interfaces.IAuthorizedClientRepository;

/**
 * CRUD for AuthorizedClient
 * 
 * @author cortes
 *
 */
@Repository
public class AuthorizedClientDao implements IAuthorizedClientRepository {

	private static final String OBJECT_KEY = "Client";

	private RedisTemplate<String, AuthorizedClient> redisTemplate;

	private HashOperations<String, String, AuthorizedClient> hashOps;

	@Autowired
	public AuthorizedClientDao(RedisTemplate redisTemplate) {

		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}

	/**
	 * Method that save info about authorized client
	 * 
	 * @param client
	 *            client - info about authorized client such as clientId,
	 *            uniqueKey, timeValidKey
	 */
	@Override
	public void saveClient(AuthorizedClient client) {

		hashOps.put(OBJECT_KEY, client.getToken(), client);
		redisTemplate.expire(OBJECT_KEY, client.getTimeValidKey(), TimeUnit.MINUTES);
	}

	/**
	 * Method that update info about authorized client
	 * 
	 * @param client
	 *            client - info about authorized client such as clientId,
	 *            uniqueKey, timeValidKey
	 */
	@Override
	public void updateClient(AuthorizedClient client) {

		hashOps.put(OBJECT_KEY, client.getToken(), client);
		redisTemplate.expire(OBJECT_KEY, client.getTimeValidKey(), TimeUnit.SECONDS);
	}

	/**
	 * Method that find record about authorized client in cash
	 * 
	 * @param id
	 *            id - key that identify some record in redis
	 * @return return - information about client in redis
	 */
	@Override
	public AuthorizedClient findClient(String id) {

		return hashOps.get(OBJECT_KEY, id);
	}

	/**
	 * Method that return all records in cash
	 * 
	 * @return
	 */
	@Override
	public Map<String, AuthorizedClient> findAllClients() {

		return hashOps.entries(OBJECT_KEY);
	}

	/**
	 * Method that delete record in cash by key id
	 * 
	 * @param id
	 */
	@Override
	public void deleteClients(String id) {

		hashOps.delete(OBJECT_KEY, id);
	}

}
