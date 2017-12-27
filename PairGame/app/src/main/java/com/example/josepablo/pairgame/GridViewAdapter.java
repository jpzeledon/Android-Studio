package com.example.josepablo.pairgame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Jose Pablo on 30/10/2017.
 */

public class GridViewAdapter extends BaseAdapter {

    //This array contains the ids of the images that appear first in the app
    private int[] charactersImages = {R.drawable.mario, R.drawable.luigi, R.drawable.bowser, R.drawable.toad,
            R.drawable.yoshi, R.drawable.peach};
    //This array contains the ids of the images that appear when the user find the couple
    private int[] charactersWinImages = {R.drawable.mario_win, R.drawable.luigi_win, R.drawable.bowser_win,
            R.drawable.toad_win, R.drawable.yoshi_win, R.drawable.peach_win};
    //This array is made to contain all the blocks
    private Block[] blocks = new Block[charactersImages.length];
    private boolean firstChoice;
    private int points;
    private Block firstBlock;
    private Block secondBlock;
    Context context;

    public GridViewAdapter(Context context)
    {
        this.context = context;
        firstChoice = false;
        points = 0;
        blocks = createBlocksArray(charactersImages, charactersWinImages);
        blocks = shuffleBlocks(blocks);
        blocks = setIdViewsInBlocks(blocks);

    }

    @Override
    public int getCount() {
        return blocks.length;
    }

    @Override
    public Object getItem(int position) {
        return blocks[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup viewGroup) {
        //Creates de blocks in the interface
        final ImageView imgView = new ImageView(context);

        imgView.setImageResource(R.drawable.bloque);
        imgView.setLayoutParams(new GridView.LayoutParams( 200,200));
        imgView.setPadding(10,10,10,10);
        imgView.setScaleType(ImageView.ScaleType.FIT_XY);
        imgView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                if(firstChoice == false && blocks[position].isTurned()==false)
                {
                    //If the user is choosing a block by first time or by first time after making a couple this code proceeds
                    firstBlock = blocks[position];
                    turnUpBlock(firstBlock, (ImageView) viewGroup.getChildAt(firstBlock.getIdView()));
                    firstChoice = true;
                }
                else
                {
                    if(blocks[position].isTurned()==false)
                    {
                        //If the user is choosing block by second time or by second time after making a couple this code proceeds
                        secondBlock = blocks[position];
                        turnUpBlock(secondBlock, imgView);
                        firstChoice = false;
                        if(compareBlocksId(firstBlock,secondBlock)==false)
                        {
                            //If the user does not find the couple this code process
                            turnDownBlock(firstBlock,(ImageView) viewGroup.getChildAt(firstBlock.getIdView()) );
                            turnDownBlock(secondBlock, imgView);
                        }
                        else
                        {
                            //If finds the couple this code process
                            points++;
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ImageView img = (ImageView) viewGroup.getChildAt(firstBlock.getIdView());
                                    img.setImageResource(secondBlock.getIdImg_win());
                                    imgView.setImageResource(secondBlock.getIdImg_win());
                                }
                            }, 600);
                        }
                    }
                }
                if(points==charactersImages.length)
                {
                    //If the user finds all the couples
                    playAgainAlert(viewGroup);
                }



            }


        });

        return  imgView;
    }

    //this function ask to the user if wants to play again
    public void playAgainAlert(final ViewGroup viewGroup)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Confirm");
        builder.setMessage("Do you want to play again?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                restart(viewGroup);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void restart (ViewGroup viewGroup)
    {
        ImageView img;
        blocks = shuffleBlocks(blocks);
        blocks = setIdViewsInBlocks(blocks);
        blocks = setAllBlocksTurnedDown(blocks);
        points = 0;
        for(int i = 0 ; i <blocks.length; i++)
        {
            img = (ImageView) viewGroup.getChildAt(i);
            img.setImageResource(R.drawable.bloque);
        }
    }

    //Shows the image behind the block
    public void turnUpBlock(Block block, ImageView imgView)
    {
        block.setTurned(true);
        imgView.setImageResource(block.getIdImg());

    }

    //Hide the image by showing the block
    public void turnDownBlock(final Block block, final ImageView imgView)
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                block.setTurned(false);
                imgView.setImageResource(R.drawable.bloque);
            }
        }, 1400);
    }


    public boolean compareBlocksId(Block block1, Block block2)
    {
        boolean result;
        if(block1.getIdImg() == block2.getIdImg())
        {
            result = true;
        }
        else
        {
            result = false;
        }
        return result;
    }

    public Block[] setIdViewsInBlocks(Block[] array)
    {
        for(int i = 0 ; i <array.length; i++)
        {
            array[i].setIdView(i);
        }
        return  array;
    }

    public Block[] setAllBlocksTurnedDown(Block[] array)
    {
        for(int i = 0 ; i <array.length; i++)
        {
            array[i].setTurned(false);
        }
        return  array;
    }

    public Block[] shuffleBlocks(Block[] array)
    {
        Random rnd = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            Block temp= array[index];
            array[index] = array[i];
            array[i] = temp;
        }
        return array;
    }

    //This function creates a block array with the arrays that contain the ids of the images
    public Block[] createBlocksArray(int[] pictures,int[] pictures_win)
    {
        Block[] array = new Block[pictures.length*2];
        int count = 0;
        for(int i = 0 ; i <array.length; i++)
        {
            if(count >= pictures.length)
            {
                count = 0;
            }
            array[i] = new Block(pictures[count],context.getResources().getResourceEntryName(pictures[count]),pictures_win[count]);
            count++;
        }
        return array;
    }
}
