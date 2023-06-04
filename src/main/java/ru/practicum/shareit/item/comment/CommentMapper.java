package ru.practicum.shareit.item.comment;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.UserRepository;

@RequiredArgsConstructor
public class CommentMapper {
    private static ItemRepository itemRepository;
    private static UserRepository userRepository;

    public static Comment makeComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setText(commentDto.getText());
        comment.setItem(itemRepository.getOne(commentDto.getItem()));
        comment.setAuthor(userRepository.getOne(commentDto.getAuthor()));
        return comment;
    }

    public static CommentDto makeCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setItem(comment.getItem().getId());
        commentDto.setAuthor(comment.getAuthor().getId());
        return commentDto;
    }
}
