package gcu.connext.petzzang.community.service;

import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.community.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    //mysql에 게시글 저장하기
    public Post downloadPost(Integer postId) {
        return postRepository.findByPostId(Long.valueOf(postId));
    }

    //게시글 삭제하기
    public String deletePost(Integer postId) {
        postRepository.deleteByPostId(Long.valueOf(postId));
        return ("success");
    }


    public Post plusLikeNum(Long postId) {
        Post posts = postRepository.findByPostId(Long.valueOf(postId));
        Long likeNum = posts.getLikeNum();
        postRepository.updateLikeNum(postId, likeNum + 1);
        return postRepository.findByPostId(Long.valueOf(postId));
    }
    public Post minusLikeNum(Long postId) {

        Post posts = postRepository.findByPostId(Long.valueOf(postId));
        Long likeNum = posts.getLikeNum();
        postRepository.updateLikeNum(postId, likeNum - 1);
        return postRepository.findByPostId(Long.valueOf(postId));
    }

    public Post updateView(Long postId) {

        Post posts = postRepository.findByPostId(Long.valueOf(postId));
        Long view = posts.getViews();
        postRepository.updateView(postId, view + 1);
        return postRepository.findByPostId(Long.valueOf(postId));
    }

}
