Feature: Verify pdf content
  @bonadaracademyPdfContent
  Scenario Outline: Verify pdf invoice content
    Given I open the web varios
    When I click on the button DOWNLOAD PDF
    And I verify the pdf content contains "<content>" next to the text "Payment due"
    Examples:
      |content|
      |30 days after invoice date|