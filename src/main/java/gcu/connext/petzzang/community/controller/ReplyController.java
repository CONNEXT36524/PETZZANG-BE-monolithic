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


@RestController
@RequestMapping("/api/community/posts")
public class ReplyController {

    @Autowired
    private ReplyService ReplyService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/posts")
    public List<Reply> getReplies(@RequestParam(value="postId",required=false) Integer key
    ) throws Exception {

        Integer postId = Integer.valueOf(key);
        //save in mysql database
        ReplyService.getReply(postId);
        return ReplyService.getReply(postId);
    }
    
    @PostMapping("/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public Reply createReply(
            @ModelAttribute ReplyDTO replyDTO
    ) throws Exception {

        //DTO to Entity
        Reply reply = modelMapper.map(replyDTO, Reply.class);

        //save in mysql database
        ReplyService.uploadReply(reply);
        //URI uriLocation = new URI("/board/" + board.getID());
        return ReplyService.uploadReply(reply);
    }

}