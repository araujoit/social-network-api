package br.com.araujo.socialnetwork.dao;

import br.com.araujo.socialnetwork.bean.SocialNetworkBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SocialNetworkDao implements Dao<SocialNetworkBean> {
    private List<SocialNetworkBean> list = new ArrayList<>();

    @Override
    public Optional<SocialNetworkBean> get(long id) {
        return Optional.ofNullable(list.get((int) id));
    }

    @Override
    public Optional<SocialNetworkBean> getByName(String name) {
        return list.stream()
                .filter(item -> item.name.equals(name.toLowerCase()))
                .findAny();
    }

    @Override
    public List<SocialNetworkBean> getAll() {
        return list;
    }

    @Override
    public void save(SocialNetworkBean socialNetworkBean) {
        list.add(socialNetworkBean);
    }

    @Override
    public void update(SocialNetworkBean socialNetworkBean, String[] params) {
        delete(socialNetworkBean);

        socialNetworkBean.name = Objects.requireNonNull(
                params[0], "Name cannot be null");

        list.add(socialNetworkBean);
    }

    @Override
    public void delete(SocialNetworkBean socialNetworkBean) {
        list.remove(socialNetworkBean);
    }
}
