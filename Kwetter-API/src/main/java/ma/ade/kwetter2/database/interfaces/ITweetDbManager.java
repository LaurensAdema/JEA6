/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.ade.kwetter2.database.interfaces;


import ma.ade.kwetter2.database.objects.Tweet;

import java.util.stream.Stream;

/**
 *
 * @author laure
 */
public interface ITweetDbManager extends IBaseDbManager<Tweet>
{
    Stream<Tweet> search(String query);
    Stream<Tweet> getTweetsOf(long id);
}
