package gcu.connext.petzzang.ranking.service;

import gcu.connext.petzzang.community.entity.Post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class setranking {

    public ArrayList<Long> calculate(List<Post> post){
        long first=0,second=0,third=0,fourth=0,fifth=0,sixth=0;
        float firstscore=0,secondscore=0,thirdscore=0,fourthscore=0,fifthscore=0,sixthscore=0;
        System.out.println(post);
        for(Post rank : post)
        {
            if(first==0L)
            {
                first=rank.getPostId();
                firstscore=rank.getViews();
            }
            else if(second==0L)
            {
                second=rank.getPostId();
                secondscore=rank.getViews();
            }
            else if(third==0L)
            {
                third=rank.getPostId();
                thirdscore=rank.getViews();
            }
            else if(fourth==0L)
            {
                fourth=rank.getPostId();
                fourthscore=rank.getViews();
            }
            else if(fifth==0L)
            {
                fifth=rank.getPostId();
                fifthscore=rank.getViews();
            }
            else if(sixth==0L)
            {
                sixth=rank.getPostId();
                sixthscore=rank.getViews();
            }
            else if((float)rank.getViews()>sixthscore)
            {
                sixth=rank.getPostId();
                sixthscore=rank.getViews();
                if(rank.getViews()>fifthscore)
                {
                    sixth=fifth;
                    sixthscore=fifthscore;
                    fifthscore=rank.getViews();
                    fifth=rank.getPostId();
                    if(rank.getViews()>fourth)
                    {
                        fifth=fourth;
                        fifthscore=fourthscore;
                        fourthscore=rank.getViews();
                        fourth=rank.getPostId();
                        if(rank.getViews()>thirdscore)
                        {
                            fourth=third;
                            fourthscore=thirdscore;
                            thirdscore=rank.getViews();
                            third=rank.getPostId();
                            if(rank.getViews()>secondscore)
                            {
                                third=second;
                                thirdscore=secondscore;
                                secondscore=rank.getViews();
                                second=rank.getPostId();
                                if(rank.getViews()>firstscore)
                                {
                                    second=first;
                                    secondscore=firstscore;
                                    firstscore=rank.getViews();
                                    first=rank.getPostId();
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(first);
        System.out.println(second);
        System.out.println(third);
        System.out.println(fourth);
        ArrayList<Long> result = new ArrayList<Long>(Arrays.asList(first,second,third,fourth,fifth,sixth));
        return result;
    }
}
