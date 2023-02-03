package gcu.connext.petzzang.community.controller;

import gcu.connext.petzzang.community.dto.ReplyDTO;
import gcu.connext.petzzang.community.entity.Reply;
import gcu.connext.petzzang.community.service.ReplyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/community/posts")
public class ReplyController {

    @Autowired
    private ReplyService ReplyService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public Reply createReply(
            @ModelAttribute ReplyDTO replyDTO
            // @RequestParam("files") List<MultipartFile> files
    ) throws Exception {

        //DTO to Entity
        Reply reply = modelMapper.map(replyDTO, Reply.class);

        //save in mysql database
        ReplyService.uploadReply(reply);
        //URI uriLocation = new URI("/board/" + board.getID());
        return ReplyService.uploadReply(reply);
    }

}