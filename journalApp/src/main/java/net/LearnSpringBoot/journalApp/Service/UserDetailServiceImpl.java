package net.LearnSpringBoot.journalApp.Service;

import net.LearnSpringBoot.journalApp.Entity.User;
import net.LearnSpringBoot.journalApp.Repository.UserEntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserEntryRepo userEntryRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userEntryRepo.findByUserName(username);
        if(user!=null){
          UserDetails userDetails=  org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
          return userDetails;
        }
        throw new UsernameNotFoundException("username not found="+username);

    }
}
