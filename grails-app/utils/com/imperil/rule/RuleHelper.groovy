package com.imperil.rule
import com.imperil.match.Match
import com.imperil.match.MatchStateEnum
import com.imperil.player.Player
import com.imperil.setup.RuleConstants


public class RuleHelper {

  public static Integer getValueAsInteger(Rule rule, Integer defaultValue) {
    if (rule == null) return defaultValue;
    return Integer.parseInt(rule.value)
  }
  public static Double getValueAsDouble(Rule rule, Double defaultValue) {
    if (rule == null) return defaultValue;
    return Double.parseDouble(rule.value)
  }
  public static Integer getValue(Rule rule, String defaultValue) {
    if (rule == null) return defaultValue;
    return rule.value
  }

  public static void initMatch(Match match, RuleGroup ruleGroup) {

    match.players.each { Player player ->
      Rule rule = ruleGroup.rules?.get(RuleConstants.RULE_KEY_STARTING_ARMY_COUNT+"."+match.players.size())
      player.armyCount = RuleHelper.getValueAsInteger(rule, 1);
      player.save(failOnError:true, flush:true)
    }
    match.state = MatchStateEnum.CHOOSING_TERRITORIES;
  }
}
