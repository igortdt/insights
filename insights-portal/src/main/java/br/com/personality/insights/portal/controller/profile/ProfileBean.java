package br.com.personality.insights.portal.controller.profile;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ibm.watson.developer_cloud.personality_insights.v3.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Content;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.ContentItem;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Profile;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.ProfileOptions;
import com.ibm.watson.developer_cloud.service.security.IamOptions;

import br.com.personality.insights.entity.BehaviorEntity;
import br.com.personality.insights.entity.NeedEntity;
import br.com.personality.insights.entity.PersonalityEntity;
import br.com.personality.insights.entity.ProfileEntity;
import br.com.personality.insights.entity.TwitterEntity;
import br.com.personality.insights.entity.ValueEntity;
import br.com.personality.insights.entity.WarningEntity;
import br.com.personality.insights.exception.AppException;
import br.com.personality.insights.helper.Twitter4JHelper;
import br.com.personality.insights.portal.controller.ConversationBean;
import br.com.personality.insights.portal.message.MessagesBean;
import br.com.personality.insights.portal.sessao.SessaoBean;
import br.com.personality.insights.service.profile.ProfileService;
import twitter4j.Status;

@Named
@ConversationScoped
public class ProfileBean implements Serializable {

	private static final long serialVersionUID = -7777609634065345042L;
	
	private static final String URL = "personality_insights.url";
	private static final String USERNAME = "personality_insights.username";
	private static final String PASSWORD = "personality_insights.password";
	private static final String IAM_APIKEY = "personality_insights.iam_apikey";
	private static final String VERSION_DATE = "2017-10-13";
	  
	@Inject
	private AppException appException;

	@Inject
	private MessagesBean messagesBean;

	@Inject
	private SessaoBean sessaoBean;
	
	private ProfileEntity profile;

	private List<ProfileEntity> profiles;

	@Inject
	private ProfileService profileService;
	
	
	@Inject
	private ConversationBean conversationBean;
	
	private int activeIndex;
	
	private int qtdTwittes;


	@PostConstruct
	public void init() {
		try {
			
			profiles = profileService.getAll();
			profile = new ProfileEntity();
			profile.setPerfilTwitter("@g1");
			qtdTwittes = 20;
			
		} catch (AppException e) {
			e.printStackTrace();
			messagesBean.convert(e);
		} catch (Exception e) {
			e.printStackTrace();
			appException.addError(e);
			messagesBean.convert(appException);
		}
	}

	

	public String salvar() {
		appException.clean();
		try {
			// 
			profileService.salvar(profile);
			messagesBean.addMessage(FacesMessage.SEVERITY_INFO,"cadastro.sucesso");
			
			return "sucesso";
		} catch (AppException e) {
			e.printStackTrace();
			messagesBean.convert(e);
		} catch (Exception e) {
			e.printStackTrace();
			appException.addError(e);
			messagesBean.convert(appException);
		}
		return "";
	}
	
	
	
	public String twitterAnalyzer() {
		appException.clean();
		try {

			String perfil = profile.getPerfilTwitter();
			Properties props = new Properties();			
			props.load(ProfileBean.class.getClassLoader().getResourceAsStream("twitter.properties"));

			PersonalityInsights service = getPersonalityInsightsService(props);

			Twitter4JHelper twitterHelper = new Twitter4JHelper(props);

			HashSet<String> langs = new HashSet<String>();
			langs.add("en");
			langs.add("es");
			langs.add("pt");
			
			System.out.println("Getting tweets for: " + perfil);
			List<Status> tweets = twitterHelper.getTweets(perfil, langs, qtdTwittes);

			System.out.println("Got " + tweets.size() + " tweets");

			List<TwitterEntity> listaTwittes = new ArrayList<TwitterEntity>();
			for (Status status : tweets) {
				//System.out.println(" " + status.getText() + " ");
				// Status status = tweets.get(i);
				// cursor = status.getId() - 1;
				
				TwitterEntity twitterEntity = new TwitterEntity();				
				BeanUtils.copyProperties(twitterEntity, status);
				System.out.println("twitter - " + twitterEntity.getText());
				
				twitterEntity.setUserTwitter(status.getUser().getName());
				if(status.getGeoLocation() != null)
					twitterEntity.setGeoLocation(status.getGeoLocation().toString());
				
				twitterEntity.setInReplyToScreenName(status.getInReplyToScreenName());
				if(status.getPlace() != null)
					twitterEntity.setPlace(status.getPlace().toString());
				
				if(status.getRetweetedStatus() != null)
					twitterEntity.setRetweetedStatus(status.getRetweetedStatus().toString());
				
				listaTwittes.add(twitterEntity);
			}

			Content content = convertTweetsToContent(tweets);
			ProfileOptions options = new ProfileOptions.Builder().content(content).build();

			Profile profile = service.profile(options).execute();

			String profileToJson = profile.toString();
			System.out.println("JSON - " + profileToJson);
			
			JSONParser parser = new JSONParser();
			Gson gson = new Gson();
			Object obj = parser.parse(profileToJson);

			JSONObject jsonObject = (JSONObject) obj;
			
			ProfileEntity profileEntity = gson.fromJson(profileToJson, ProfileEntity.class);
			profileEntity.setDataCriacao(new Date());
			profileEntity.setTwittes(listaTwittes);
			profileEntity.setPerfilTwitter(perfil);
						
			Type typePersonalityEntity = new TypeToken<List<PersonalityEntity>>() {
			}.getType();
			
			Type typeNeedEntity = new TypeToken<List<NeedEntity>>() {
			}.getType();
			
			Type typeValueEntity = new TypeToken<List<ValueEntity>>() {
			}.getType();
			
			Type typeBehaviorEntity = new TypeToken<List<BehaviorEntity>>() {
			}.getType();
			
			Type typeWarningEntity = new TypeToken<List<WarningEntity>>() {
			}.getType();
			
			List<PersonalityEntity> personalitys = gson.fromJson(jsonObject.get("personality").toString(), typePersonalityEntity);
			List<NeedEntity> needs = gson.fromJson(jsonObject.get("needs").toString(), typeNeedEntity);
			List<ValueEntity> values = gson.fromJson(jsonObject.get("values").toString(), typeValueEntity);
			List<BehaviorEntity> behaviors = gson.fromJson(jsonObject.get("behavior").toString(), typeBehaviorEntity);
			List<WarningEntity> warnings = gson.fromJson(jsonObject.get("warnings").toString(), typeWarningEntity);
			
			for(PersonalityEntity personalityEntity : personalitys){
				personalityEntity.setProfile(profileEntity);
			}
			for(NeedEntity needEntity : needs){
				needEntity.setProfile(profileEntity);
			}
			for(ValueEntity valueEntity : values){
				valueEntity.setProfile(profileEntity);
			}
			for(BehaviorEntity behaviorEntity : behaviors){
				behaviorEntity.setProfile(profileEntity);
			}
			for(WarningEntity warningEntity : warnings){
				warningEntity.setProfile(profileEntity);
			}
			for(TwitterEntity twitterEntity : listaTwittes){
				twitterEntity.setProfile(profileEntity);
			}
		
			
			profileEntity.setPersonalitys(personalitys);
			profileEntity.setNeeds(needs);
			profileEntity.setValues(values);
			profileEntity.setBehaviors(behaviors);
			profileEntity.setWarnings(warnings);
			
			messagesBean.addMessage(FacesMessage.SEVERITY_INFO, "analise.sucesso");
			this.profile = profileService.salvar(profileEntity);
			
			return "sucesso";
		} catch (AppException e) {
			e.printStackTrace();
			messagesBean.convert(e);
		} catch (Exception e) {
			e.printStackTrace();
			appException.addError(e);
			messagesBean.convert(appException);
		}
		return "";
	}
	
	
	public String prepararEdicao(ProfileEntity profile) {
		try {
			conversationBean.begin();
			this.profile = profile;
			
			return "/profile-detalhe.xhxml";
			
		} catch (Exception e) {
			e.printStackTrace();
			appException.addError(e);
			messagesBean.convert(appException);
		}
		return "";
	}
	
	
	
	public void pesquisar() {

		appException.clean();

		try {

			profiles = profileService.getAll();
			if (profiles == null || profiles.isEmpty()) {
				
				messagesBean.addMessage(FacesMessage.SEVERITY_WARN,"nenhum.registro.encontrado");
			}

		} catch (AppException e) {
			e.printStackTrace();
			messagesBean.convert(e);
		} catch (Exception e) {
			e.printStackTrace();
			appException.addError(e);
			messagesBean.convert(appException);
		}

	}

	/**
	 * Gets the personality insights service.
	 *
	 * @param props
	 *            the props
	 * @return the personality insights service
	 * @throws Exception
	 *             the exception
	 */
	public static PersonalityInsights getPersonalityInsightsService(Properties props) throws Exception {
		PersonalityInsights service = null;

		String username = props.getProperty(USERNAME);
		String password = props.getProperty(PASSWORD);
		String iamApiKey = props.getProperty(IAM_APIKEY);
		String url = props.getProperty(URL);

		if (iamApiKey != null && !iamApiKey.isEmpty()) {
			IamOptions options = new IamOptions.Builder().apiKey(iamApiKey).build();

			service = new PersonalityInsights(VERSION_DATE, options);
		} else if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			throw new Exception(
					"iam_apikey or username and password should be specified in the twitter.properties file");
		} else {
			service = new PersonalityInsights(VERSION_DATE, username, password);
		}

		if (url != null && !url.isEmpty()) {
			service.setEndPoint(url);
		}

		return service;
	}

	/**
	 * Convert a list of tweets to content.
	 *
	 * @param tweets
	 *            the tweets
	 * @return The tweets as content
	 * @throws Exception
	 *             the exception
	 */
	public static Content convertTweetsToContent(List<Status> tweets) throws Exception {
		List<ContentItem> contentItems = new ArrayList<ContentItem>();

		for (Status status : tweets) {
			String language = status.getLang();
			if(language.equals("pt")){
				language = "en";
			}
			ContentItem contentItem = new ContentItem.Builder().id(Long.toString(status.getId()))
					.forward(status.isRetweet()).reply((status.getInReplyToScreenName() != null))
					.language(language).contenttype("text/plain")
					.content(status.getText().replaceAll("[^(\\x20-\\x7F)]*", ""))
					.created(status.getCreatedAt().getTime()).build();
			contentItems.add(contentItem);
		}

		return new Content.Builder().contentItems(contentItems).build();
	}

	
	public void onTabChange() throws AppException{
		
	}
	
	public ProfileEntity getProfileEntity() {
		return profile;
	}

	public void setProfileEntity(ProfileEntity grupo) {
		this.profile = grupo;
	}

	public List<ProfileEntity> getProfileEntitys() {
		return profiles;
	}

	public void setProfileEntitys(List<ProfileEntity> grupos) {
		this.profiles = grupos;
	}



	public ProfileEntity getProfile() {
		return profile;
	}



	public void setProfile(ProfileEntity profile) {
		this.profile = profile;
	}



	public List<ProfileEntity> getProfiles() {
		return profiles;
	}



	public void setProfiles(List<ProfileEntity> profiles) {
		this.profiles = profiles;
	}



	public int getActiveIndex() {
		return activeIndex;
	}



	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}



	public int getQtdTwittes() {
		return qtdTwittes;
	}



	public void setQtdTwittes(int qtdTwittes) {
		this.qtdTwittes = qtdTwittes;
	}

	

}
