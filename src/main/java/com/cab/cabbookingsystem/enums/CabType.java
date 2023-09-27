package com.cab.cabbookingsystem.enums;

public enum CabType {
    MICRO{
        @Override
        public int getNumberOfSeats() {
            return 3;
        }

        @Override
        public int getPerKmFare() {
            return 35;
        }
    }
    , MINI{
        @Override
        public int getNumberOfSeats() {
            return 4;
        }

        @Override
        public int getPerKmFare() {
            return 50;
        }
    }, SUV{
        @Override
        public int getNumberOfSeats() {
            return 6;
        }

        @Override
        public int getPerKmFare() {
            return 70;
        }
    };

    public abstract int getNumberOfSeats();
    public abstract int getPerKmFare();
}
