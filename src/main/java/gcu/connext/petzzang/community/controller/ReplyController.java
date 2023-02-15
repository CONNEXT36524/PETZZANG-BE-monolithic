package gcu.connext.petzzang.community.controller;

import gcu.connext.petzzang.community.dto.ReplyDTO;
import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.community.entity.Reply;
import gcu.connext.petzzang.community.service.ReplyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;


@RestController
@RequestMapping("/api/community")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping("/get-replies")
    public List<Reply> getReplies(@RequestParam(value="postId",required=false) Integer postIdKey
    ) throws Exception {

        Integer postId = Integer.valueOf(postIdKey);

        return replyService.getReply(postId);
    }
    @GetMapping("/get-nreplies")
    public List<Reply> getNReplies(@RequestParam(value="postId",required=false) Integer postIdKey,
                                   @RequestParam(value="bundleId",required=false) Integer bundleIdKey
    ) throws Exception {

        Integer postId = Integer.valueOf(postIdKey);
        Integer bundleId = Integer.valueOf(bundleIdKey);
        System.out.println("postIdKey--" +postIdKey);
        System.out.println("bundleIdKey--" + bundleIdKey);
        return replyService.getNReply(postId, bundleId);
    }

    @PostMapping("/post-replies")
    @ResponseStatus(HttpStatus.CREATED)
    public Reply createReply(
            @ModelAttribute ReplyDTO replyDTO
    ) throws Exception {

        //DTO to Entity
        Reply reply = modelMapper.map(replyDTO, Reply.class);


        return replyService.uploadReply(reply);
    }

    @PostMapping("/post-nreplies")
    @ResponseStatus(HttpStatus.CREATED)
    public Reply createNReply(
            @ModelAttribute ReplyDTO replyDTO
    ) throws Exception {

        //DTO to Entity
        Reply reply = modelMapper.map(replyDTO, Reply.class);

        return replyService.uploadNReply(reply);
    }

    @DeleteMapping("/delete-replies")
    public int deleteReply(@RequestParam(name="replyId") Integer key
    ) throws Exception {

        Long replyId = Long.valueOf(key);

        //URI uriLocation = new URI("/board/" + board.getID());
        return replyService.deleteReply(replyId);
    }

}