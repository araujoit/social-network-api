package br.com.araujo.socialnetwork.dao;

import br.com.araujo.socialnetwork.bean.SocialNetworkBean;
import br.com.araujo.socialnetwork.bean.UserBean;
import org.apache.commons.lang3.NotImplementedException;

import java.util.*;
import java.util.stream.Collectors;

public class UserDao implements Dao<UserBean> {
    //    private List<UserBean> list = new ArrayList<>();
    private Map<Integer, UserBean> map = new HashMap<>();

    @Override
    public Optional<UserBean> get(long id) {
        return Optional.ofNullable(map.get((int) id));
    }

    @Override
    public List<UserBean> getAll() {
        throw new NotImplementedException("Eita");
    }

    public List<UserBean> getAll(SocialNetworkBean socialNetworkBean) {
        return map.values().stream()
                .filter(userBean -> userBean.getSocialNetwork().equals(socialNetworkBean))
                .collect(Collectors.toList());
    }

    @Override
    public void save(UserBean userBean) {
        map.put((int) userBean.getId(), userBean);
    }

    @Override
    public void update(UserBean userBean, String[] params) {
        delete(userBean);

        userBean.setName(Objects.requireNonNull(
                params[0], "Name cannot be null"));

        map.put((int) userBean.getId(), userBean);
    }

    @Override
    public void delete(UserBean userBean) {
        map.remove((int) userBean.getId());
    }

    @Override
    public Optional<UserBean> getByName(String userName) {
        return map.values().stream()
                .filter(f ->
                        f.getName().equals(userName)
                ).findFirst();

//        map.values().stream().map(UserBean::getName).filter(f -> f.equals(name)).collect(Collectors.toList());
//        return map.values().stream()
//                .filter(item -> item.getName().equals(name.toLowerCase()))
//                .findAny();
    }
}
