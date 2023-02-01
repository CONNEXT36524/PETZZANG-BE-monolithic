package gcu.connext.petzzang.community.service;


import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.community.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

//    public List<Post> findAll() {
//        List<Post> posts = new ArrayList<>();
//        boardRepository.findAll().forEach(e -> posts.add(e));
//        return posts;
//    }

    /* search */
    @Transactional
    public List<Post> search(String keyword) {
        List<Post> postsList = boardRepository.findByPetContainingOrKindContainingOrTitleNameContainingOrContentContaining(keyword, keyword, keyword, keyword);

        return postsList;
    }



}