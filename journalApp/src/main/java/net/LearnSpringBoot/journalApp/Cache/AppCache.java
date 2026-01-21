package net.LearnSpringBoot.journalApp.Cache;

import net.LearnSpringBoot.journalApp.Entity.ConfigJournalAppEntity;
import net.LearnSpringBoot.journalApp.Repository.ConfigJournalAppRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class AppCache {
    public Map<String,String> APP_CACHE;

    @Autowired
  private ConfigJournalAppRepo configJournalAppRepo;

@PostConstruct
    public void init(){
    APP_CACHE= new HashMap<>();
    List<ConfigJournalAppEntity> all=configJournalAppRepo.findAll();
    for(ConfigJournalAppEntity configJournalAppEntity :all){
        APP_CACHE.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
    };
    }
}
