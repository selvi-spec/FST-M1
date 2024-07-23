import pytest

@pytest.mark.parametrize("earned, spent, expected", [ (30, 10, 20), (20, 2, 18), ])

def test_transactions(wallet_amount, earned, spent, expected):

    # Add money to your wallet
    wallet_amount += earned

    # Subtract amount from wallet
    wallet_amount -= spent

    # Assertion
    assert wallet_amount == expected