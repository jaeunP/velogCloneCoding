package com.clone.velog.service.post;

import java.util.Optional;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clone.velog.itf.CrudInterface;
import com.clone.velog.models.entity.post.PostEntity;
import com.clone.velog.models.network.Header;
import com.clone.velog.models.network.request.PostReq;
import com.clone.velog.models.network.response.PostRes;
import com.clone.velog.repository.PostRepository;
import com.clone.velog.service.img.ImgService;

@Service
public class PostService implements CrudInterface<PostReq, PostRes> {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ImgService imgService;

    @Override
    public Header<PostRes> create(Header<PostReq> request) {
        if(request.getData().getPostTitle() == null || request.getData().getPostDescription() == null || request.getData().getPostUserIndex() == 0){
            return Header.ERROROfNull();
        }
        return response(postRepository.save(request.getData().toEntity()));
    }

    private Header<PostRes> response(PostEntity post) {
        PostRes postRes = PostRes.builder()
                .postIndex(post.getPostIndex())
                .postTitle(post.getPostTitle())
                .postDescription(post.getPostDescription())
                .postImgName(post.getPostImgName())
                .postImgOriName(post.getPostImgOriName())
                .postImgURL(post.getPostImgURL())
                .postTempSave(post.getPostTempSave())
                .postRegDate(post.getPostRegDate())
                .postUpdateDate(post.getPostUpdateDate())
                .postTag(post.getPostTag())
                .postHits(post.getPostHits())
                .postLove(post.getPostLove())
                .build();
        return Header.OK(postRes);
    }
    
    @Override
    public Header<PostRes> read(Integer postIndex) {
        
        Optional<PostEntity> post = postRepository.findBypostIndex(postIndex);
        if(post.isEmpty()){
            return Header.ERROROfNull();
        }
        PostEntity postEntity = post.get();
        
        return response(postEntity);
    }

    @Override
    public Header<PostRes> update(Header<PostReq> request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Header delete(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    
    
}
