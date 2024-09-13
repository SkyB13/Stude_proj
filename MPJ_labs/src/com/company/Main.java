package com.company;
import mpi.*;

import java.util.Arrays;

//Lab_1
public class Main {
    public static void main(String[] args) throws Exception {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int[] Send_data = {1, 2, 3};
        int[] Recv_Data = new int[2];

        int tag = 1;

        if (rank % 2 == 0) {
            if (rank + 1 != size) {
                MPI.COMM_WORLD.Send(Send_data, 0, 2, MPI.INT, rank + 1, tag);
            }
        } else {
            if (rank != 0) {
                MPI.COMM_WORLD.Recv(Recv_Data, 0, 2, MPI.INT, rank - 1, tag);
                System.out.println("Сообщение отправил: " + (rank - 1) +", принял: " + Arrays.toString(Recv_Data) + "; сообщение получил: " + rank);
            }
        }
    }
}