Feature: Managing dialogs and popups
  @bonadaracademyRanWarningDialog
  Scenario Outline: Managing random warning dialog
    Given I open the web varios
    When enter a name "<name>" in the dialog
    Examples:
      |name|
      |camelia|