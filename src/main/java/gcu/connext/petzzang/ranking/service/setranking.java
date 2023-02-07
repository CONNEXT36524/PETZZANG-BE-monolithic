package gcu.connext.petzzang.ranking.service;

import gcu.connext.petzzang.community.entity.Post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class setranking {

    public ArrayList<Post> calculate(List<Post> post){
        Post first=null,second=null,third=null,fourth=null,fifth=null,sixth=null;
        float firstscore=0,secondscore=0,thirdscore=0,fourthscore=0,fifthscore=0,sixthscore=0;
        System.out.println(post);
        for(Post rank : post)
        {
            if(first==null)
            {
                first=rank;
                firstscore=rank.getViews();
            }
            else if(second==null)
            {
                second=rank;
                secondscore=rank.getViews();
            }
            else if(third==null)
            {
                third=rank;
                thirdscore=rank.getViews();
            }
            else if(fourth==null)
            {
                fourth=rank;
                fourthscore=rank.getViews();
            }
            else if(fifth==null)
            {
                fifth=rank;
                fifthscore=rank.getViews();
            }
            else if(sixth==null)
            {
                sixth=rank;
                sixthscore=rank.getViews();
            }
            else if((float)rank.getViews()>sixthscore)
            {
                sixth=rank;
                sixthscore=rank.getViews();
                if(rank.getViews()>fifthscore)
                {
                    sixth=fifth;
                    sixthscore=fifthscore;
                    fifthscore=rank.getViews();
                    fifth=rank;
                    if(rank.getViews()>fourthscore)
                    {
                        fifth=fourth;
                        fifthscore=fourthscore;
                        fourthscore=rank.getViews();
                        fourth=rank;
                        if(rank.getViews()>thirdscore)
                        {
                            fourth=third;
                            fourthscore=thirdscore;
                            thirdscore=rank.getViews();
                            third=rank;
                            if(rank.getViews()>secondscore)
                            {
                                third=second;
                                thirdscore=secondscore;
                                secondscore=rank.getViews();
                                second=rank;
                                if(rank.getViews()>firstscore)
                                {
                                    second=first;
                                    secondscore=firstscore;
                                    firstscore=rank.getViews();
                                    first=rank;
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
        return new ArrayList<Post>(Arrays.asList(first,second,third,fourth,fifth,sixth));
    }
}
