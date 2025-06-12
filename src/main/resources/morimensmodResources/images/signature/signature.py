from PIL import Image

def pad_image_to_1024x1024(image_path, output_path):
    # 讀入原始圖片 600x830
    original = Image.open(image_path).convert("RGBA")

    # 確認原始圖片尺寸
    orig_w, orig_h = original.size
    print(f"Original size: {orig_w}x{orig_h}")

    # 設定 padding 與新圖大小
    pad_left = 211
    pad_top = 98
    target_width = 1024
    target_height = 1024

    # 計算新的圖片右下角位置
    new_w = pad_left + orig_w
    new_h = pad_top + orig_h

    # 確保圖片不會超出 1024x1024
    if new_w > target_width or new_h > target_height:
        raise ValueError("Padded image would exceed 1024x1024 dimensions.")

    # 建立透明背景的 1024x1024 圖片
    padded = Image.new("RGBA", (target_width, target_height), (0, 0, 0, 0))

    # 貼上原始圖片
    padded.paste(original, (pad_left, pad_top))

    # 儲存新圖
    padded.save(output_path)
    print(f"Padded image saved to {output_path}")

# 用法範例
pad_image_to_1024x1024("Defend.png", "Defend_s_p.png")