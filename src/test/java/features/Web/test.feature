@tag1
Feature: Book a VISA appointment
  Scenario Outline: VISA booking experience

    Given User is on the VISA booking landing page
    When User User clicks on TerminBuchen
    And agrees to the terms and conditions
    And clicks on Weiter
    And User fills the form
    Then User sends mail regarding status of booking
#    And clicks on NEXT